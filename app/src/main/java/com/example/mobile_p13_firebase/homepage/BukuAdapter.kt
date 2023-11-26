import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile_p12.tugas.editbuku.EditBukuActivity
import com.example.mobile_p13_firebase.R

import com.example.mobile_p13_firebase.homepage.Buku
import com.google.firebase.firestore.FirebaseFirestore

class BukuAdapter : RecyclerView.Adapter<BukuAdapter.BukuViewHolder>() {

    private var bukus: List<Buku> = listOf()
    private val firestore = FirebaseFirestore.getInstance()
    private val bukuCollection = firestore.collection("buku")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_buku, parent, false)
        return BukuViewHolder(view)
    }

    override fun onBindViewHolder(holder: BukuViewHolder, position: Int) {
        val currentBuku = bukus[position]

        holder.textViewBuku.text = currentBuku.buku
        holder.textViewPenulis.text = "Ditulis oleh: ${currentBuku.penulis}"
        holder.textViewGenre.text = currentBuku.genre
        holder.textViewHarga.text = "Rp ${currentBuku.harga}"

        holder.btEdit.setOnClickListener {
            try {
                val intent = Intent(holder.itemView.context, EditBukuActivity::class.java)
                showToast(currentBuku.harga.toString(),holder)
                intent.putExtra("id", currentBuku.id)
                intent.putExtra("buku", currentBuku.buku)
                intent.putExtra("penulis", currentBuku.penulis)
                intent.putExtra("genre", currentBuku.genre)
                intent.putExtra("harga", currentBuku.harga.toString())
                holder.itemView.context.startActivity(intent)
            }catch (e: Exception){
                showToast(e.toString(),holder)
                Log.d("ERR", e.toString())
            }

        }

        holder.btDelete.setOnClickListener {
            showYesNoAlertDialog(
                holder.itemView.context,
                "Apakah anda yakin akan menghapus ${currentBuku.buku}",
                DialogInterface.OnClickListener { _, _ ->
                    deleteBuku(currentBuku.id,holder)
                }
            )
        }
    }

    private fun deleteBuku(id: String, holder: BukuViewHolder) {
        bukuCollection.document(id)
            .delete()
            .addOnSuccessListener {
                Toast.makeText(
                    holder.itemView.context,
                    "Buku berhasil dihapus",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    holder.itemView.context,
                    "Error deleting document: $e",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    override fun getItemCount(): Int {
        return bukus.size
    }

    fun setBukus(bukus: List<Buku>) {
        this.bukus = bukus
        notifyDataSetChanged()
    }

    inner class BukuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewBuku: TextView = itemView.findViewById(R.id.bukuTextView)
        val textViewPenulis: TextView = itemView.findViewById(R.id.penulisTextView)
        val textViewGenre: TextView = itemView.findViewById(R.id.genreTextView)
        val textViewHarga: TextView = itemView.findViewById(R.id.hargaTextView)
        val btEdit: Button = itemView.findViewById(R.id.itemBtEdit)
        val btDelete: Button = itemView.findViewById(R.id.itemBtDelete)
    }

    fun showYesNoAlertDialog(
        context: Context,
        message: String,
        onYesClickListener: DialogInterface.OnClickListener
    ) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setCancelable(false)

        alertDialogBuilder.setPositiveButton("Yes", onYesClickListener)
        alertDialogBuilder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showToast(message: String, holder: BukuViewHolder) {
        Toast.makeText(holder.itemView.context, message, Toast.LENGTH_SHORT).show()
    }

}

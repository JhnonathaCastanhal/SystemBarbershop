package br.com.jhnonathacastanhal.jhnoapp

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class DisciplinaAdapter (
    val disciplinas: List<Disciplina>,
    val onClick: (Disciplina) -> Unit

) : RecyclerView.Adapter<DisciplinaAdapter.DisciplinasViewHolder>()


{

    class DisciplinasViewHolder(view: View):
        RecyclerView.ViewHolder(view){

        val card_nome: TextView
        val card_img: ImageView
        val card_view: CardView

        init{
            card_nome =
            view.findViewById<TextView>(R.id.cardNome)

            card_img =
            view.findViewById<ImageView>(R.id.cardImg)

            card_view =
            view.findViewById<CardView>(R.id.card_disciplinas)}
    }

    override fun getItemCount() = this.disciplinas.size

    override fun onCreateViewHolder(
        parent: ViewGroup, ViewType: Int): DisciplinasViewHolder{

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_disciplina, parent,false)

        return DisciplinasViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DisciplinasViewHolder,
        posicao: Int) {

        val context = holder.itemView.context

        val disciplina = disciplinas[posicao]

        holder.card_nome.text = disciplina.nome

        Picasso.with(context).load(disciplina.foto)
            .into(holder.card_img,
                object: com.squareup.picasso.Callback{
                    override fun onSuccess() {
                    }

                    override fun onError() {

                    }
                })
        holder.itemView.setOnClickListener{onClick(disciplina)}

    }

}
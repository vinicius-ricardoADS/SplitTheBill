package br.edu.ifsp.ads.splitthebill.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.ads.splitthebill.R
import br.edu.ifsp.ads.splitthebill.databinding.TileMemberBinding
import br.edu.ifsp.ads.splitthebill.model.Member

class PaidPersonAdapter (context: Context, private val memberList: MutableList<Member>
): ArrayAdapter<Member> (context, R.layout.tile_member, memberList) {

    private lateinit var tcb: TileMemberBinding




    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val member: Member = memberList[position]

        var total = 0.0

        if (memberList.size > 0) {
             total = memberList
                .map { m -> m.amountPaid }
                .reduce { a, b -> a + b }
        }

        val maxPayMember = total / memberList.size.toDouble()



        var tileMemberView = convertView

        if (tileMemberView == null) {
            tcb = TileMemberBinding.inflate(
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater,
                parent,
                false
            )
            tileMemberView = tcb.root

            val tcvh: TileMemberViewHolder = TileMemberViewHolder(
                tcb.nameTv,
                tcb.amoutTv
            )

            tileMemberView.tag = tcvh
        }
        val mustReceive = member.amountPaid - maxPayMember
        if (member.amountPaid > maxPayMember) {
            (tileMemberView.tag as TileMemberViewHolder).nameTv.text = member.name
            (tileMemberView.tag as TileMemberViewHolder).amoutPaid.text = "Must receive ${String.format("%.2f", mustReceive)}"
        }
        if (member.amountPaid < maxPayMember) {
            (tileMemberView.tag as TileMemberViewHolder).nameTv.text = member.name
            (tileMemberView.tag as TileMemberViewHolder).amoutPaid.text = "Must pay: ${String.format("%.2f", maxPayMember - member.amountPaid)}"
        }

        return tileMemberView
    }

    private data class TileMemberViewHolder (
        val nameTv: TextView,
        val amoutPaid: TextView
    )
}
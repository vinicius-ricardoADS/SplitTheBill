package br.edu.ifsp.ads.splitthebill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.ads.splitthebill.R
import br.edu.ifsp.ads.splitthebill.databinding.TileMemberBinding
import br.edu.ifsp.ads.splitthebill.model.Member

class MemberAdapter (context: Context, private val memberList: MutableList<Member>
): ArrayAdapter<Member> (context, R.layout.tile_member, memberList) {

    private lateinit var tcb: TileMemberBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val member: Member = memberList[position]

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
                tcb.emailTv
            )

            tileMemberView.tag = tcvh
        }

        (tileMemberView.tag as TileMemberViewHolder).nameTv.text = member.name
        (tileMemberView.tag as TileMemberViewHolder).amoutPaid.text = member.amountPaid.toString()

        return tileMemberView
    }

    private data class TileMemberViewHolder (
        val nameTv: TextView,
        val amoutPaid: TextView
    )
}
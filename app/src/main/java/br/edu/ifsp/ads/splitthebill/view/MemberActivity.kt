package br.edu.ifsp.ads.splitthebill.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import br.edu.ifsp.ads.splitthebill.databinding.ActivityMemberBinding
import br.edu.ifsp.ads.splitthebill.model.Member

class MemberActivity : BasicActivity () {

    private val mb: ActivityMemberBinding by lazy {
        ActivityMemberBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mb.root)

        val receivedMember = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_MEMBER, Member::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_MEMBER)
        }

        with (mb) {
            saveBt.setOnClickListener {
                setResult(RESULT_OK, Intent().putExtra(EXTRA_MEMBER, Member(
                    receivedMember?.id,
                    nameEt.text.toString(),
                    itemsEt.text.toString(),
                    amountPaidEt.text.toString().toDouble()
                )))
                finish()
            }

            receivedMember?.let {
                with(receivedMember) {
                    nameEt.setText(name)
                    itemsEt.setText(items)
                    amountPaidEt.setText(amountPaid.toString())
                }
            }

            val viewMember = intent.getBooleanExtra(EXTRA_VIEW_MEMBER, false)
            with (mb) {
                nameEt.isEnabled = !viewMember
                itemsEt.isEnabled = !viewMember
                amountPaidEt.isEnabled = !viewMember
                saveBt.visibility = if (viewMember) View.GONE else View.VISIBLE
            }
        }
    }
}
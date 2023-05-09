package br.edu.ifsp.ads.splitthebill.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.ads.splitthebill.adapter.MemberAdapter
import br.edu.ifsp.ads.splitthebill.controller.MemberController
import br.edu.ifsp.ads.splitthebill.databinding.ActivityCalculateInDebtBinding
import br.edu.ifsp.ads.splitthebill.model.Member

class CalculateInDebtActivity () : BasicActivity () {

    private val cb: ActivityCalculateInDebtBinding by lazy {
        ActivityCalculateInDebtBinding.inflate(layoutInflater)
    }

    private val memberList: MutableList<Member> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(cb.root)
    }
}
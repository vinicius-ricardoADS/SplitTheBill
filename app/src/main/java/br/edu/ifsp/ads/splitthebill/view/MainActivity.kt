package br.edu.ifsp.ads.splitthebill.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.ads.splitthebill.R
import br.edu.ifsp.ads.splitthebill.adapter.MemberAdapter
import br.edu.ifsp.ads.splitthebill.adapter.PaidPersonAdapter
import br.edu.ifsp.ads.splitthebill.controller.MemberController
import br.edu.ifsp.ads.splitthebill.databinding.ActivityMainBinding
import br.edu.ifsp.ads.splitthebill.model.Member
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : BasicActivity() {

    private val amb: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val memberList: MutableList<Member> = mutableListOf()

    private val memberAdapter: MemberAdapter by lazy {
        MemberAdapter (this, memberList)
    }

    private val paidPersonAdapter: PaidPersonAdapter by lazy {
        PaidPersonAdapter (this, memberList)
    }

    private lateinit var parl: ActivityResultLauncher<Intent>

    private val memberController: MemberController by lazy {
        MemberController(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(amb.root)

        amb.membersLv.adapter = memberAdapter

        memberController.getMembers()

        parl = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
            if (it?.resultCode == RESULT_OK) {
                val member = when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> it.data?.getParcelableExtra(EXTRA_MEMBER, Member::class.java)
                    else -> it.data?.getParcelableExtra<Member>(EXTRA_MEMBER)
                }

                member?.let { _member ->
                    val position = memberList.indexOfFirst { it.id == _member.id }
                    when {
                        position != -1 -> {
                            memberController.editMember(_member)
                            memberList[position] = _member
                            Toast.makeText(this, "Member edit!", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            memberController.insertMember(_member)
                            Toast.makeText(this, "Member added!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    memberAdapter.notifyDataSetChanged()
                    paidPersonAdapter.notifyDataSetChanged()
                }
            }
        }

        registerForContextMenu(amb.membersLv)

        amb.membersLv.onItemClickListener = AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
            val member = memberList[p2]
            parl.launch(Intent(this@MainActivity, MemberActivity::class.java).putExtra(EXTRA_MEMBER, member).putExtra(EXTRA_VIEW_MEMBER, true))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addMember -> {
                parl.launch(Intent(this, MemberActivity::class.java))
                true
            }
            R.id.calculate -> {
                val view: View = layoutInflater.inflate(R.layout.activity_calculate_in_debt, null)
                val dialog = BottomSheetDialog(this)
                dialog.setContentView(view)
                val listView = view.findViewById<ListView>(R.id.calculateDebtMembers)
                val total = memberList
                    .map { member -> member.amountPaid }
                    .reduce { a, b -> a + b }
                supportActionBar?.subtitle = "Totality: R$ ${String.format("%.2f", total)}"
                listView.adapter = paidPersonAdapter
                dialog.show()
                true
            }
            else -> false
        }
    }

    override fun onCreateContextMenu (
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position
        val member = memberList[position]
        return when (item.itemId) {
            R.id.removeMemberMi -> {
                memberList.removeAt(position)
                memberController.removeMember(member)
                memberAdapter.notifyDataSetChanged()
                paidPersonAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Contato removido!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.editMemberMi -> {
                parl.launch(Intent(this, MemberActivity::class.java).putExtra(EXTRA_MEMBER, member))
                true
            }
            R.id.detailsMemberMi -> {
                parl.launch(Intent(this, MemberActivity::class.java).putExtra(EXTRA_MEMBER, member).putExtra(EXTRA_VIEW_MEMBER, true))
                true
            }
            else -> false
        }
    }

    fun updateMemberList (_memberList: MutableList<Member>) {
        memberList.clear()
        memberList.addAll(_memberList)
        memberAdapter.notifyDataSetChanged()
        paidPersonAdapter.notifyDataSetChanged()
    }
}
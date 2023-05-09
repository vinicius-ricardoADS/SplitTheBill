package br.edu.ifsp.ads.splitthebill.controller

import androidx.room.Room
import br.edu.ifsp.ads.splitthebill.model.Member
import br.edu.ifsp.ads.splitthebill.model.MemberDaoRoom
import br.edu.ifsp.ads.splitthebill.model.MembersDao
import br.edu.ifsp.ads.splitthebill.view.MainActivity

class MemberController (private val mainActivity: MainActivity) {

    private val memberDaoImpl: MembersDao = Room.databaseBuilder(
        mainActivity, MemberDaoRoom::class.java, MemberDaoRoom.MEMBER_DATABASE_FILE
    ).build().getMemberDao()

    fun insertMember (member: Member) {
        Thread {
            memberDaoImpl.createMember(member)
            val list = memberDaoImpl.retrieveMembers()
            mainActivity.runOnUiThread {
                mainActivity.updateMemberList(list)
            }
        }.start()
    }

    fun getMembers () {
        Thread {
            val list = memberDaoImpl.retrieveMembers()
            mainActivity.runOnUiThread {
                mainActivity.updateMemberList(list)
            }
        }.start()
    }

    fun editMember (member: Member) {
        Thread {
            memberDaoImpl.updateMember(member)
            val list = memberDaoImpl.retrieveMembers()
            mainActivity.runOnUiThread {
                mainActivity.updateMemberList(list)
            }
        }.start()
    }

    fun removeMember (member: Member) {
        Thread {
            memberDaoImpl.deleteMember(member)
            val list = memberDaoImpl.retrieveMembers()
            mainActivity.runOnUiThread {
                mainActivity.updateMemberList(list)
            }
        }.start()
    }
}
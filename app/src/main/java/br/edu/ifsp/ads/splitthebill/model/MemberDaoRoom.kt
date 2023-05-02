package br.edu.ifsp.ads.splitthebill.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (entities = [Member::class], version = 1)
abstract class MemberDaoRoom: RoomDatabase () {

    companion object {
        const val MEMBER_DATABASE_FILE = "members_room"
    }

    abstract fun getMemberDao (): MembersDao
}
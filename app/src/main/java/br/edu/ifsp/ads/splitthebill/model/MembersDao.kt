package br.edu.ifsp.ads.splitthebill.model

import androidx.room.*

@Dao
interface MembersDao {

    @Insert
    fun createMember (member: Member): Member?

    @Query ("SELECT * FROM Member")
    fun retrieveMembers (): MutableList<Member>

    @Update
    fun updateMember (member: Member): Int

    @Delete
    fun deleteMember (member: Member): Int
}
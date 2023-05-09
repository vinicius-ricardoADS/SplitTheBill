package br.edu.ifsp.ads.splitthebill.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.edu.ifsp.ads.splitthebill.R
import java.sql.SQLException

class MemberDaoSqlite (context: Context): MembersDao {

    companion object {
        private const val MEMBER_DATABASE_FILE = "contacts"
        private const val MEMBER_TABLE = "contact"
        private const val ID_COLUMN = "id"
        private const val NAME_COLUMN = "name"
        private const val ITEMS_COLUMN = "items"
        private const val AMOUD_PAID = "amount_paid"

        private const val CRATE_MEMBER_TABLE_STATEMENT =
            "create table if not exists $MEMBER_TABLE (" +
                    "$ID_COLUMN integer primary key autoincrement, " +
                    "$NAME_COLUMN text not null, " +
                    "$ITEMS_COLUMN text not null, " +
                    "$AMOUD_PAID text not null, " +
                    ");"
    }

    private val memberSqliteDatabase: SQLiteDatabase

    init {
        // Criando ou abrindo o banco
        memberSqliteDatabase = context.openOrCreateDatabase(MEMBER_DATABASE_FILE,
            Context.MODE_PRIVATE, null)
        try {
            memberSqliteDatabase.execSQL(CRATE_MEMBER_TABLE_STATEMENT)
        } catch (se: SQLException) {
            Log.e(context.getString(R.string.app_name), se.toString())
        }
    }

    override fun createMember(member: Member) {
        memberSqliteDatabase.insert(MEMBER_TABLE, null, member.toContentValues()).toInt()
    }

    override fun retrieveMembers(): MutableList<Member> {
        val memberList = mutableListOf<Member>()

        val cursor = memberSqliteDatabase.rawQuery("select * from $MEMBER_TABLE order by $NAME_COLUMN", null)

        while (cursor.moveToFirst()) {
            memberList.add(cursor.rowToContact())
        }
        cursor.close()

        return memberList
    }

    override fun updateMember(member: Member) = memberSqliteDatabase.update(
        MEMBER_TABLE, member.toContentValues(), "$ID_COLUMN = ?", arrayOf(member.id.toString())
    )

    override fun deleteMember(member: Member) = memberSqliteDatabase.delete(
        MEMBER_TABLE, "$ID_COLUMN = ?", arrayOf(member.id.toString())
    )

    private fun Member.toContentValues () = with (ContentValues()) {
        put(NAME_COLUMN, name)
        put(ITEMS_COLUMN, items.toString())
        put(AMOUD_PAID, amountPaid.toString())
        this
    }

    private fun Cursor.rowToContact () = Member(
        getInt(getColumnIndexOrThrow(ID_COLUMN)),
        getString(getColumnIndexOrThrow(NAME_COLUMN)),
        getString(getColumnIndexOrThrow(ITEMS_COLUMN)),
        getDouble(getColumnIndexOrThrow(AMOUD_PAID))
    )
}
package br.edu.ifsp.ads.splitthebill.view

import androidx.appcompat.app.AppCompatActivity

sealed class BasicActivity: AppCompatActivity () {

    protected val EXTRA_MEMBER = "Member"
    protected val EXTRA_VIEW_MEMBER = "ViewMember"
}
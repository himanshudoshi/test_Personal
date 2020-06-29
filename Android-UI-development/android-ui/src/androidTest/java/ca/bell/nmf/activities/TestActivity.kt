package ca.bell.nmf.activities

import android.os.Bundle
import ca.bell.nmf.ui.context.BaseActivity
import ca.bell.nmf.ui.R

class TestActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.testing_activity_layout)
    }

}
package blackbird.com.simple.acvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import blackbird.com.recyclerviewdemo.R
import blackbird.com.recyclerviewdemo.databinding.ActivityViewModelBinding
import com.google.android.material.snackbar.Snackbar

class ViewModelActivity : AppCompatActivity() {


    lateinit var databind: ActivityViewModelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this)[ProfileLiveDataViewModel::class.java]
        //ViewModelProvider(this).get(ProfileLiveDataViewModel::class.java)
        databind = DataBindingUtil.setContentView<ActivityViewModelBinding>(this, R.layout.activity_view_model)

        setSupportActionBar(databind.toolbar)
        //databind.toolbar.title = title
        //databind.fab.setOnClickListener(::print)
        databind.fab.setOnClickListener {
            Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        databind.viewModel = viewModel
        databind.lifecycleOwner=this

    }
}
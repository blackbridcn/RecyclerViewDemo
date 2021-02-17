package blackbird.com.simple.vmdrl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import blackbird.com.recyclerviewdemo.R
import blackbird.com.recyclerviewdemo.databinding.ActivityRecyclerViewModelBinding

class RecyclerViewModelActivity : AppCompatActivity() {


    lateinit var databind: ActivityRecyclerViewModelBinding

    lateinit var viewModel: VmdrlViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databind = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view_model)
        viewModel = ViewModelProvider(this)[VmdrlViewModel::class.java]
        databind.lifecycleOwner=this
    }
}
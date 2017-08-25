package com.example.acer.listingsaved

import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    val displayData = "Data"

    var input: EditText? = null
    var display: RecyclerView? = null

    var sharedPreference: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    var layoutManager : LinearLayoutManager? = null

    var adapter = ListAdapter();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);

        input = findViewById<EditText>(R.id.input)

        display = findViewById<RecyclerView>(R.id.list)


        layoutManager = LinearLayoutManager(this)

        display?.layoutManager = layoutManager

        adapter.list = retrive();

        display?.adapter = adapter
    }

    class ListAdapter : RecyclerView.Adapter<ListAdapter.Holder>() {

        var list: ArrayList<String> = ArrayList<String>();

        override fun onBindViewHolder(holder: Holder?, position: Int) {
            holder?.list!!.text = list[position]
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): Holder {

            val view = LayoutInflater.from(parent?.context).inflate(R.layout.display, parent, false);
            return Holder(view);
        }

        override fun getItemCount(): Int {
            return list.size
        }

        fun update (data:String,manger:LinearLayoutManager){
            val index = list.size;
            list.add(index,data);
            notifyItemInserted(index);
            manger.scrollToPosition(index)

        }






        class Holder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
            val list: TextView = itemView?.findViewById<TextView>(R.id.rec) as TextView
        }

    }

    fun save(v: View) {
        editor = sharedPreference?.edit()
        //editor?.putString(displayData,input?.text.toString())

        val a: ArrayList<String> = retrive()
        a.add(0, input?.text.toString());
        val s: Set<String> = HashSet<String>(a);
        editor?.putStringSet(displayData, s);
        editor?.apply()
        load()
        input?.setText("")

    }

    fun load() {
        adapter.update(input?.text.toString(), layoutManager!!)
    }

    fun retrive(): ArrayList<String> {
        val s = sharedPreference?.getStringSet(displayData, null);
        if (s == null) {
            return ArrayList<String>();
        }
        return ArrayList<String>(s);
    }
}

package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//A bridge that tells the recyclerview how to display the data we give it

class TaskItemAdapter(val listOfItems : List<String>, val longClickedListener : OnLongClickListener) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener
    {
        fun onItemLongClicked(position: Int)
    }

    //on createViewHolder is to inflate the layout
    //here we are inflating the simple_list_item_1.xml which
    //is by default stored in Android Studio
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemAdapter.ViewHolder {

        val context = parent.context //get the context

        val inflater = LayoutInflater.from(context) //then the layoutInflater inflates the layout

        val taskBeingDisplayed = inflater.inflate(android.R.layout.simple_list_item_1,parent,false)

        return ViewHolder(taskBeingDisplayed)
    }

    override fun onBindViewHolder(holder: TaskItemAdapter.ViewHolder, position: Int) {

        //get the data model based on position

        val item : String = listOfItems[position]

        holder.textView.text = item

    }

    override fun getItemCount(): Int {
        return listOfItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        //Store references to our views

        val textView : TextView

        init
        {
            textView = itemView.findViewById<TextView>(android.R.id.text1)

            itemView.setOnLongClickListener{

                longClickedListener.onItemLongClicked(adapterPosition)
                Log.i("testTag", "Item is Long Clicked $adapterPosition")

                true
            }
        }

    }
}





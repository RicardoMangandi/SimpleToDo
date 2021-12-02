package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils.readLines
import org.apache.commons.io.FileUtils.writeLines
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity()
{

    var listOfTasks = mutableListOf<String>()
    lateinit var taskItemAdapter : TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener
        {
            override fun onItemLongClicked(position: Int)
            {
                //1. Remove the item from the list
                //2. Notify the adapter that our data set has changed
                listOfTasks.removeAt(position)
                taskItemAdapter.notifyItemRemoved(position)
                saveItems()
            }

        }

        loadItems()

        val myButton = findViewById<Button>(R.id.button)

        val editText = findViewById<EditText>(R.id.editTextAddToDo)

        val myRecyclerView = findViewById<RecyclerView>(R.id.myRecyclerView)

        taskItemAdapter = TaskItemAdapter(listOfTasks,onLongClickListener)

        myRecyclerView.adapter = taskItemAdapter

        //the LinearLayoutManager needs a context passed in as its parameter
        myRecyclerView.layoutManager = LinearLayoutManager(this)

        myButton.setOnClickListener{

            val getText = editText.text.toString()

            //add the item in our adapter
            listOfTasks.add(getText)

            //notify the adapter
            taskItemAdapter.notifyItemInserted(listOfTasks.size - 1)

            //reset text field
            editText.setText("")

            saveItems()
        }


    }

    //Save the data that the user has inputted


    //Create a method to get the file we need

    fun getDataFile() : File
    {
        //Every line is going to represent a specific task in our list of tasks
        return File(filesDir,"data.txt")
    }


    //Load the items by reading every line the data file

    fun loadItems()
    {
        try
        {
            listOfTasks = readLines(getDataFile(), Charset.defaultCharset())
        }
        catch (ioException: IOException)
        {
            ioException.printStackTrace()
        }

    }

    //Save items by writing them into our data file

    fun saveItems()
    {
      try
      {
          writeLines(getDataFile(), listOfTasks)
      }
      catch (ioException: IOException)
      {
          ioException.printStackTrace()
      }


    }



}
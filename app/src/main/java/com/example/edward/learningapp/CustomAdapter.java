package com.example.edward.learningapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by edward on 7/22/16.
 *
 *Custom adapter for GridView. Used for displaying word images and all categories
 */
public class CustomAdapter extends BaseAdapter implements Filterable{


    private ArrayList<Category> catData;
    private ArrayList<Category> filterList;
    private CustomFilter filter;
    private Context context;
    private LayoutInflater inflater;
    private Word word;
    private boolean isCategory;
    private boolean isList;




    public CustomAdapter(Context context, ArrayList<Category> catData, Boolean isList) {
        // TODO Auto-generated constructor stub
        this.catData = catData;
        this.context = context;
        this.isCategory=true;
        this.isList=isList;
        this.filterList=catData;
    }

    public CustomAdapter(Context context, Word word){
        this.context=context;
        this.word = word;
        this.isCategory=false;
    }

    @Override
    public int getCount(){
        if(isCategory) {
            return catData.size();
        }
        else{
            return word.getImgs().size();
        }
    }

    @Override
    public Object getItem(int position) {
        return catData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setisList(Boolean isList){
        this.isList = isList;
    }

    public class ViewHolder{
        TextView name;
        ImageView image;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent){
        View catView = convertView;

        //using adapter to display all categories
        if(convertView == null && isCategory) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(!isList) {
                catView = inflater.inflate(R.layout.customcategory, null);
            }
            else{
                catView = inflater.inflate(R.layout.listcategory, null);
            }
        }

        //using adapter to display all word images
        if(convertView == null && !isCategory){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            catView = inflater.inflate(R.layout.word_image, null);
        }
        //Object to downsize image
        final ImageDownSize display = new ImageDownSize((context));

        //Category Menu View
        if(isCategory) {
            ViewHolder categoryHolder = new ViewHolder();
            int percentage = catData.get(position).getPercentage();

            //Using listView
            if(isList){
                categoryHolder.name = (TextView) catView.findViewById(R.id.textList);
                categoryHolder.image = (ImageView) catView.findViewById(R.id.imageList);
                categoryHolder.name.setText(catData.get(position).getCatName());
                TextView progressText = (TextView) catView.findViewById(R.id.progressTextL);
                progressText.setText(percentage+"%");
                GridView gv = (GridView)parent;
                gv.setNumColumns(1);
            }

            //Using gridView
            else {
                categoryHolder.name = (TextView) catView.findViewById(R.id.catNameGrid);
                categoryHolder.image = (ImageView) catView.findViewById(R.id.imageGrid);
                categoryHolder.name.setText(catData.get(position).getCatName());
                TextView progressText = (TextView) catView.findViewById(R.id.progressTextG);
                progressText.setText(percentage+"%");
                GridView gv = (GridView)parent;
                gv.setNumColumns(2);
            }

            ProgressBar progressBar = (ProgressBar) catView.findViewById(R.id.simpleProgressBar);
            progressBar.setProgress(percentage);
            display.setScaledImage(categoryHolder.image, catData.get(position).getCatimg());
            catView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, word_activity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("category", catData.get(position));
                    ((Activity)context).startActivityForResult(i, 1);

                }
            });
        }
        //WordView
        else{
            ImageView wordImage = (ImageView) catView.findViewById(R.id.wordImage);
            //wordImage.setImageBitmap(word.getImgs().get(position));
            display.setScaledImage(wordImage, word.getImgs().get(position));
        }

        return catView;
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new CustomFilter();
        }
        return filter;
    }

    //For search
    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if(charSequence != null && charSequence.length() != 0){
                //Convert to uppercase
                charSequence = charSequence.toString().toUpperCase();
                ArrayList<Category> filters = new ArrayList<>();

                for(int i = 0; i < filterList.size(); i++){
                    if(filterList.get(i).getCatName().toUpperCase().contains(charSequence)){
                        filters.add(filterList.get(i));
                    }

                }
                results.count = filters.size();
                results.values=filters;
            }
            else{
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            catData = (ArrayList<Category>) filterResults.values;
            notifyDataSetChanged();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){


    }
}
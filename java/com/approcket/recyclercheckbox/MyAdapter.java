package com.approcket.recyclercheckbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

        Context c;
        private MainActivity mainActivity;
        private ArrayList<Employee> checkedEmployee;

        public MyAdapter(Context c, ArrayList<Employee> employes) {
            this.c = c;
            this.mainActivity=(MainActivity)c;
            this.checkedEmployee = employes;
        }

        @NonNull
        @Override
        public MyAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist, null);
            MyHolder holder = new MyHolder(v,mainActivity);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyAdapter.MyHolder holder, final int position) {
            Employee employee=checkedEmployee.get(position);
            holder.nameTxt.setText(employee.getName());
            holder.desc.setText(employee.getDesc());
            holder.img.setImageResource(employee.getImage());
            if(employee.getSelected()){
                holder.myCheckBox.setChecked(true);

            }
            else
            {
                holder.myCheckBox.setChecked(false);
            }

           if(mainActivity.position==position){
               //holder.myCheckBox.setChecked(true);
               mainActivity.position=-1;
           }

           if (mainActivity.inActionMode){
               Anim anim=new Anim(100,holder.relativeLayout);
               anim.setDuration(300);
               holder.relativeLayout.setAnimation(anim);
           }else{
               Anim anim=new Anim(0,holder.relativeLayout);
               anim.setDuration(300);
               holder.relativeLayout.setAnimation(anim);
               checkedEmployee.get(position).setSelected(false);
           }

           holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View view) {
                   mainActivity.startSelection(position);
                   return true;
               }
           });

           holder.myCheckBox.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   mainActivity.check(view,position);
                   if(checkedEmployee.get(position).getSelected()){
                       checkedEmployee.get(position).setSelected(false);

                   }
                   else {
                       checkedEmployee.get(position).setSelected(true);
                       Toast.makeText(c, checkedEmployee.get(position).getName(), Toast.LENGTH_SHORT).show();

                   }
                   notifyDataSetChanged();

               }
           });
        }

        @Override
        public int getItemCount() {
            return checkedEmployee.size();
        }

        class MyHolder extends RecyclerView.ViewHolder {

            ImageView img;
            TextView nameTxt, desc;
            CheckBox myCheckBox;
            MainActivity mainActivity;
            RelativeLayout relativeLayout;
            CardView cardView;

            public MyHolder(@NonNull View itemView, MainActivity mainActivity) {
                super(itemView);
                nameTxt = itemView.findViewById(R.id.nameTextView);
                desc = itemView.findViewById(R.id.descriptionTextView);
                img = itemView.findViewById(R.id.imageView);
                this.mainActivity=mainActivity;
                relativeLayout=itemView.findViewById(R.id.hideontap);
                cardView=itemView.findViewById(R.id.cardView);
                myCheckBox = itemView.findViewById(R.id.myCheckBooox);

            }
        }
        class Anim extends Animation {
            private int width, startWidth;
            private View view;

            public Anim(int width,View view){
                this.width=width;
                this.view=view;
                this.startWidth=view.getWidth();
            }

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                int newWidth=startWidth + (int) ((width-startWidth)+interpolatedTime);
                view.getLayoutParams().width=newWidth;
                view.requestLayout();
                super.applyTransformation(interpolatedTime, t);
            }

            @Override
            public boolean willChangeBounds() {
                return super.willChangeBounds();
            }
        }
    }


package com.onedata.sunexchange.logintask.helper;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.onedata.sunexchange.logintask.R;
import com.onedata.sunexchange.logintask.bean.ModeratorDetail;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends BaseAdapter implements ListAdapter {
    String[] UserName;
    String Email;
    LayoutInflater inflater;
    private List<ModeratorDetail> list ;
    Context mcontext;
    Dialog myDialog;

    public MyAdapter(Context context, List<ModeratorDetail> List ) {

        this.mcontext=context;
        this.list=List;
        //Toast.makeText(mcontext,"================="+mcontext,Toast.LENGTH_LONG).show();

        /*
        this.UserName = userName;
        this.Email = email;
        this.list=List;*/
    }

   /* @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }*/

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
        //just return 0 if your list items do not have an Id variable.
    }




    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.view_moderator_screen, null);
            myDialog = new Dialog(mcontext);
            String userStatusStr;
            TextView username = (TextView) row.findViewById(R.id.userName);
            TextView email = (TextView) row.findViewById(R.id.email);
            ImageView status=row.findViewById(R.id.status);
            ImageView viewIMG=row.findViewById(R.id.view);
            ImageView delete=row.findViewById(R.id.delete);
                if(list.get(position).getUsername()==null){
                    status.setVisibility(View.VISIBLE);
                }else{
                    status.setVisibility(View.GONE);

                }
            viewIMG.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myDialog.setContentView(R.layout.popmoderator);
                    ImageView exit = myDialog.findViewById(R.id.exit);
                    TextView userName = myDialog.findViewById(R.id.userName);
                    TextView firstName = myDialog.findViewById(R.id.firstName);
                    TextView lastName = myDialog.findViewById(R.id.lastName);
                    TextView email = myDialog.findViewById(R.id.email);
                    TextView userStatus = myDialog.findViewById(R.id.userStatus);
                    TextView companyName = myDialog.findViewById(R.id.companyName);
                    Log.d("list", "onClick: ++++++++++++++++++++++++++++++++++++++++++"+list.get(position));
                        userName.setText(list.get(position).getUsername());
                        firstName.setText(list.get(position).getFirstName());
                        lastName.setText(list.get(position).getLastName());
                        email.setText(list.get(position).getEmail());
                        companyName.setText(list.get(position).getCompanyName());
                    if(list.get(position).getUsername()==null){
                        String userStatusStr="user active";
                        userStatus.setText(userStatusStr);
                    }else{
                        String userStatusStr="user inactive";
                        userStatus.setText(userStatusStr);
                    }

                    exit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            myDialog.dismiss();
                        }
                    });
                    myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    myDialog.show();
                }
            });
            Log.d("size", "getView: "+list.size());
            username.setText(list.get(position).getUsername());
            email.setText(list.get(position).getEmail());
            return row;
    }


}
package dsardy.in.greedygameintern;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Shubham on 3/28/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {


    List<ResolveInfo> pkgAppsList ;
    Context context;

    public DataAdapter(List<ResolveInfo> pkgAppsList, Context c) {

        this.pkgAppsList=pkgAppsList;
        this.context=c;
    }


    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataAdapter.ViewHolder holder, int position) {

        final ResolveInfo resolveInfo = pkgAppsList.get(position);

        holder.text.setText(resolveInfo.loadLabel(context.getPackageManager()));
        holder.icon.setImageDrawable(resolveInfo.loadIcon(context.getPackageManager()));

        // Set a click listener for CardView
        holder.c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the intent to launch the specified application
                Intent intent = context.getPackageManager().getLaunchIntentForPackage(resolveInfo.activityInfo.packageName);
                if(intent != null){
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context, " Launch Error.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return pkgAppsList.size();
    }


    // view holder class

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView icon;
        TextView text;
        CardView c;


        public ViewHolder(View view) {
            super(view);

            //declare
            icon = (ImageView)view.findViewById(R.id.app_logo);
            text = (TextView)view.findViewById(R.id.app_name);
            c = (CardView)view.findViewById(R.id.card_view);

        }


    }









}
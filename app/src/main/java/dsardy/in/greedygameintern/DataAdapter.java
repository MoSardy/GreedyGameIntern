package dsardy.in.greedygameintern;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
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
    String text;

    public DataAdapter(List<ResolveInfo> pkgAppsList, Context c, String textinput) {

        this.pkgAppsList=pkgAppsList;
        this.context=c;
        this.text = textinput;
    }


    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DataAdapter.ViewHolder holder, int position) {

        final ResolveInfo resolveInfo = pkgAppsList.get(position);

        //get the spannable text according to the typed query
        Spannable theName = getSpannableAppName(text,resolveInfo.loadLabel(context.getPackageManager()).toString());

        //setting icon and name
        holder.text.setText(theName);
        holder.icon.setImageDrawable(resolveInfo.loadIcon(context.getPackageManager()));

        // Setting a click listener for CardView
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

    private Spannable getSpannableAppName(String text, String name) {

        Spannable sb = new SpannableString(name);
        sb.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), name.toLowerCase().indexOf(text.toLowerCase()),
                name.toLowerCase().indexOf(text.toLowerCase()) + text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return sb;
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
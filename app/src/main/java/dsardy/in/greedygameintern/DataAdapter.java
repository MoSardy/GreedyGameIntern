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

        Spannable theName = getSpannableAppName(text,resolveInfo.loadLabel(context.getPackageManager()).toString());

        holder.text.setText(theName);
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

    private Spannable getSpannableAppName(String text, String name) {

        String appname;


        /*
        SpannableStringBuilder sb = new SpannableStringBuilder(name);
        appname = sb.toString();

        StyleSpan b = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
        ColorStateList blueColor = new ColorStateList(new int[][] { new int[] {}}, new int[] { Color.BLUE });
        TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, blueColor, null);


        int indexi = 0;

        int startPos = text.toLowerCase(Locale.US).indexOf(name.toLowerCase(Locale.US));

        if(startPos!=-1){
            sb.setSpan(highlightSpan, 0, 0 + text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE); // make text input characters Bold
            appname = sb.toString();
        }

                return  appname;



*/

        // ignore case and accents
        // the same thing should have been done for the search text
        /*String normalizedText = Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();

        int start = normalizedText.indexOf(text);
        if (start < 0) {
            // not found, nothing to to
            return name;
        } else {
            // highlight each appearance in the original text
            // while searching in normalized text
            Spannable highlighted = new SpannableString(name);
            while (start >= 0) {
                int spanStart = Math.min(start, name.length());
                int spanEnd = Math.min(start + text.length(), name.length());

                highlighted.setSpan(new BackgroundColorSpan(<"">), spanStart, spanEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                start = normalizedText.indexOf(text, spanEnd);
            }

            return highlighted.toString();
        }*/

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
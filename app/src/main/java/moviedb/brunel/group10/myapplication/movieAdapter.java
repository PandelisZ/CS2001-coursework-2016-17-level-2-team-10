package moviedb.brunel.group10.myapplication;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class movieAdapter extends RecyclerView.Adapter<movieAdapter.ViewHolder> {

    private Context context;
    private List<movieList> MovieList;

    public movieAdapter(List<movieList> MovieList, Context context){
        this.MovieList = MovieList;
        this.context = context;
    }
    @Override
    public movieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout,parent,false);
        return  new ViewHolder(v,context,MovieList);
    }

    @Override
    public void onBindViewHolder(movieAdapter.ViewHolder holder, int position) {

        movieList movielist = MovieList.get(position);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.title.setText(movielist.getTitle());
        holder.rating.setText(movielist.getRating());

        Picasso.with(context)
                .load(movielist.getPoster())
                .resize(100, 150)
                .skipMemoryCache()
                .into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return MovieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        Context context;
        TextView title;
        TextView rating;
        ImageView poster;
        LinearLayout linearLayout;
        List<movieList> MovieList;

        public ViewHolder(View itemView,Context context, List<movieList> MovieList) {
            super(itemView);
            this.context=context;
            this.MovieList=MovieList;

            cv = (CardView)itemView.findViewById(R.id.cv);
            linearLayout = (LinearLayout)itemView.findViewById(R.id.linear_v2);
            title = (TextView)itemView.findViewById(R.id.title);
            rating = (TextView)itemView.findViewById(R.id.rating);
            poster = (ImageView)itemView.findViewById(R.id.poster);
        }
    }
}

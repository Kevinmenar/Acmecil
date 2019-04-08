package acmecil.project.projima.com.acmecil.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


import acmecil.project.projima.com.acmecil.Medicamentos.ImageResult;
import acmecil.project.projima.com.acmecil.R;

public class PublicityAdapter extends RecyclerView.Adapter<PublicityAdapter.PublicityView> {
    private View v;
    List<ImageResult> list; //path de las imagenes
    private Context context;

    public PublicityAdapter(List<ImageResult> list, Context c){
        this.list = list;
        this.context = c;
    }

    @NonNull
    @Override
    public PublicityView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_search,null,false);
        return new PublicityView(this.v);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicityView holder, int position) {
        ImageResult currentResult = list.get(position);
        Picasso.with(context).load("https://i.pinimg.com/originals/f2/fc/15/f2fc15c5046e05788a4040d1ecc35d0a.jpg").into(holder.localImage);
       // holder.localImage.setImageResource(R.drawable.publicity);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PublicityView extends RecyclerView.ViewHolder{

        ImageView localImage;

        public PublicityView(@NonNull View itemView) {
            super(itemView);
            localImage = itemView.findViewById(R.id.search_list_item_image);

        }
    }
}
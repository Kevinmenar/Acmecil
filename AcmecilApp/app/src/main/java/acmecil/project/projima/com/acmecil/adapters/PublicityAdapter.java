package acmecil.project.projima.com.acmecil.adapters;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


import acmecil.project.projima.com.acmecil.Medicamentos.ImageResult;
import acmecil.project.projima.com.acmecil.R;

public class PublicityAdapter extends RecyclerView.Adapter<PublicityAdapter.PublicityView> {
    private View v;
    List<ImageResult> list; //path de las imagenes

    public PublicityAdapter(List<ImageResult> list){this.list = list;}

    @NonNull
    @Override
    public PublicityView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_search,null,false);
        return new PublicityView(this.v);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicityView holder, int position) {
        ImageResult currentResult = list.get(position);
        holder.localImage.setImageResource(R.drawable.publicity);
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

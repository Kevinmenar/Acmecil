package acmecil.project.projima.com.acmecil;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.PharmacyViewHolder> {

    private View v;


    @NonNull
    @Override
    public PharmacyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //this.v = LayoutInflater.from(parent.getContext());
        return new PharmacyViewHolder(this.v);
    }

    @Override
    public void onBindViewHolder(@NonNull PharmacyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class PharmacyViewHolder extends RecyclerView.ViewHolder{

        TextView localName, localAdress, price, medicineName;
        ImageView localImage;

        public PharmacyViewHolder(@NonNull View itemView) {
            super(itemView);
            //localAdress = itemView.findViewById();
        }
    }


}

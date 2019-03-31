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

import java.util.List;

import acmecil.project.projima.com.acmecil.Controller;
import acmecil.project.projima.com.acmecil.Medicamentos.SearchResult;
import acmecil.project.projima.com.acmecil.R;


public class ResultListAdapter extends RecyclerView.Adapter<ResultListAdapter.PharmacyViewHolder> {

    private View v;
    private Context context;
    List<SearchResult> list;

    public ResultListAdapter(List<SearchResult> list){this.list = list;}


    @NonNull
    @Override
    public PharmacyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_pharmacy_listitem,null,false);
        return new PharmacyViewHolder(this.v);
    }

    @Override
    public void onBindViewHolder(@NonNull PharmacyViewHolder holder, int position) {
        SearchResult currentResult = list.get(position);
        holder.localName.setText(currentResult.getPharmacyName());
        holder.localAdress.setText(currentResult.getPharmacyAdress());
        holder.price.setText(String.format("%d",currentResult.getPrice()));
        holder.medicineName.setText(list.get(position).getMedicineName());
        switch (Controller.getInstance().getSessionRole()){
            case COMMON_USER:
            {
                holder.editPrice.setVisibility(View.GONE);
                holder.reportPrice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: Abrir Dialog con EditTex
                    }
                });
                break;
            }
            case ADMINISTRATOR:
            {
                holder.reportPrice.setVisibility(View.GONE);
                holder.editPrice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: Abrir Dialog con EditTex
                    }
                });
                break;
            }
        }
        //Cambiar imagen
    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PharmacyViewHolder extends RecyclerView.ViewHolder{

        TextView localName, localAdress, price, medicineName;
        ImageView localImage;
        ImageView editPrice, reportPrice;

        public PharmacyViewHolder(@NonNull View itemView) {
            super(itemView);
            localName = itemView.findViewById(R.id.pharmacylist_item_name);
            localAdress = itemView.findViewById(R.id.pharmacylist_item_address);
            price = itemView.findViewById(R.id.pharmacylist_item_price_placeholder);
            medicineName = itemView.findViewById(R.id.pharmacylist_item_bottom_medicine);
            localImage = itemView.findViewById(R.id.pharmacylist_item_image);
            editPrice = itemView.findViewById(R.id.pharmacylist_item_edit_price_imagebutton);
            reportPrice = itemView.findViewById(R.id.pharmacylist_item_report_imagebutton);
        }
    }


}

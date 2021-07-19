package com.example.jic2021;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jic2021.entities.Reportes;

import java.util.List;

/**
 * El recycler adapter es el que se encarga de crear
 * las reglas y manejar el contenido del recyclerView
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    //Lista de Reportes
    private List<Reportes> listaReportes;

    public RecyclerAdapter(List<Reportes> listaReportes) {
        this.listaReportes = listaReportes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.solicitudes_item, parent, false);

        //Se crea una instancia del viewHolder que va a contener el item que le estamos enviando
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    //Este es el metodo que realiza el binding de la data dentro del viewHolder, aqui se realiza la magia
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id_solicitud.setText(listaReportes.get(position).getIdentificador());
        holder.fecha_solicitud.setText(listaReportes.get(position).getFechaString());

        //Muestra la imagen del estado
        switch(listaReportes.get(position).getEstado()){
            case "Finalizado":
                holder.estado.setImageResource(R.drawable.completed_asset);
                break;
            case "Pendiente":
                holder.estado.setImageResource(R.drawable.pending_asset);
                break;
            case "Rechazado":
                holder.estado.setImageResource(R.drawable.denied_asset);
                break;
            default:
                break;
        }
    }

    //Este metodo representa la cantidad de elementos que tendra el recyclerView
    @Override
    public int getItemCount() {
        return (int) listaReportes.size();
    }

    //Dentro del viewHolder se encuentran todos los objetos dentro de la vista del item
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView id_solicitud, fecha_solicitud;
        ImageView estado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            estado = itemView.findViewById(R.id.estado);
            id_solicitud = itemView.findViewById(R.id.solicitud_title);
            fecha_solicitud = itemView.findViewById(R.id.fecha_solicitud);
        }
    }

    public interface OnItemListener{
        void OnItemClick(int position);
    }
}

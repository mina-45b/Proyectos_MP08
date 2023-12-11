package com.example.messageapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messageapp.databinding.FragmentBusquedaBinding;
import com.example.messageapp.databinding.ViewholderMensajesRecibidosBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Busqueda extends Fragment {

    FragmentBusquedaBinding binding;

    MensajesViewModel mensajesViewModel;
    NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return (binding = FragmentBusquedaBinding.inflate(inflater, container, false)).getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mensajesViewModel = new ViewModelProvider(requireActivity()).get(MensajesViewModel.class);
        navController = Navigation.findNavController(view);

        MensajesAdapter mensajesAdapter= new MensajesAdapter();

        binding.recyclerViewBusqueda.setAdapter(mensajesAdapter);

        binding.recyclerViewBusqueda.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT  | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int posicion = viewHolder.getAdapterPosition();
                Mensaje mensaje = mensajesAdapter.obtenerMensaje(posicion);
                mensajesViewModel.actualizar(mensaje, mensaje.esFavorito, true);

            }
        }).attachToRecyclerView(binding.recyclerViewBusqueda);

        obtenerMensajes().observe(getViewLifecycleOwner(), new Observer<List<Mensaje>>() {
            @Override
            public void onChanged(List<Mensaje> mensajes) {
                mensajesAdapter.establecerLista(mensajes);
            }
        });


        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mensajesViewModel.establecerTerminoBusqueda(newText);
                return false;
            }
        });


    }

    LiveData<List<Mensaje>> obtenerMensajes() {return mensajesViewModel.buscar();}

    //Formato de hora
    private String formatoFechaHora (long timestampMillis) {
        Date date = new Date(timestampMillis);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(date);
    }

    class MensajesAdapter extends RecyclerView.Adapter<MensajesViewHolder> {

        List<Mensaje> mensajes;

        @NonNull
        @Override
        public MensajesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MensajesViewHolder(ViewholderMensajesRecibidosBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MensajesViewHolder holder, int position) {


            Mensaje mensaje = mensajes.get(position);

            holder.binding.usuario.setText(mensaje.usuario);
            holder.binding.asunto.setText(mensaje.asunto);
            holder.binding.vistaPrevia.setText(mensaje.contenido);
            //Formato hora fecha
            String horaFormateada = formatoFechaHora(mensaje.hora);
            holder.binding.fecha.setText(horaFormateada);

            if (mensaje.esFavorito) {
                holder.binding.favorito.setImageResource(R.drawable.estrellaclic);
            } else {
                holder.binding.favorito.setImageResource(R.drawable.estrellanoclic);
            }

            holder.binding.favorito.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Cambiar el color al hacer clic
                    if (mensaje.esFavorito) {
                        holder.binding.favorito.setImageResource(R.drawable.estrellanoclic); // Cambiar estrella no clicada
                        mensajesViewModel.actualizar(mensaje, false, mensaje.esArchivado);
                    } else {
                        holder.binding.favorito.setImageResource(R.drawable.estrellaclic); // Cambia estrella clicada
                        mensajesViewModel.actualizar(mensaje, true, mensaje.esArchivado);
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mensajesViewModel.seleccionar(mensaje);
                    navController.navigate(R.id.action_mostrarMensajeFragment);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mensajes != null ? mensajes.size() : 0;
        }

        public void establecerLista(List<Mensaje> mensajes){
            this.mensajes = mensajes;
            notifyDataSetChanged();
        }

        public Mensaje obtenerMensaje(int posicion){
            return mensajes.get(posicion);
        }
    }

    static class MensajesViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderMensajesRecibidosBinding binding;

        public MensajesViewHolder(ViewholderMensajesRecibidosBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
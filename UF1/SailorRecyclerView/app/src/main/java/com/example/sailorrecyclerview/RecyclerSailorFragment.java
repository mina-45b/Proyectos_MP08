package com.example.sailorrecyclerview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.example.sailorrecyclerview.databinding.FragmentRecyclerSailorBinding;
import com.example.sailorrecyclerview.databinding.ViewholderSailorBinding;

import java.util.List;


public class RecyclerSailorFragment extends Fragment {

    private FragmentRecyclerSailorBinding binding;
    private SailorViewModel sailorViewModel;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentRecyclerSailorBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sailorViewModel = new ViewModelProvider(requireActivity()).get(SailorViewModel.class);
        navController = Navigation.findNavController(view);

        binding.irANuevaSailor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_recyclerSailorFragment_to_nuevaSailorFragment);
            }
        });

        ElementosAdapter elementosAdapter = new ElementosAdapter();
        binding.recyclerView.setAdapter(elementosAdapter);

        sailorViewModel.obtener().observe(getViewLifecycleOwner(), new Observer<List<Sailor>>() {
            @Override
            public void onChanged(List<Sailor> sailors) {
                elementosAdapter.establecerLista(sailors);
            }
        });

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
                Sailor sailor = elementosAdapter.obtenerElemento(posicion);
                sailorViewModel.eliminar(sailor);

            }
        }).attachToRecyclerView(binding.recyclerView);

    }

    class SailorViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderSailorBinding binding;

        public SailorViewHolder(ViewholderSailorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    class ElementosAdapter extends RecyclerView.Adapter<SailorViewHolder> {

        List<Sailor> sailors;

        @NonNull
        @Override
        public SailorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SailorViewHolder(ViewholderSailorBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull SailorViewHolder holder, int position) {

            Sailor sailor = sailors.get(position);

            holder.binding.nombre.setText(sailor.nombre);
            holder.binding.image.setImageResource(sailor.image);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sailorViewModel.seleccionar(sailor);
                    navController.navigate(R.id.action_recyclerSailorFragment_to_mostrarSailorFragment);
                }
            });

        }

        @Override
        public int getItemCount() {
            return sailors != null ? sailors.size() : 0;
        }

        public void establecerLista(List<Sailor> sailors){
            this.sailors = sailors;
            notifyDataSetChanged();
        }

        public Sailor obtenerElemento(int posicion){
            return sailors.get(posicion);
        }
    }

}
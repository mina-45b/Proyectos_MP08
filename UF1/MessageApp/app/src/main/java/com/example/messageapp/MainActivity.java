package com.example.messageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.messageapp.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    NavController navController;
    DrawerLayout drawerLayout;

    FloatingActionButton fab;

   Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());

        setSupportActionBar(binding.toolbar);
        drawerLayout = binding.drawerLayout;

        NavigationView navigationView = binding.navView;
        navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment)).getNavController();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                // Top-level destinations:
                R.id.homeRecibidosFragment, R.id.favoritosFragment, R.id.enviadosRecyclerMensajeFragment, R.id.archivadosFragment
        )
                .setOpenableLayout(binding.drawerLayout)
                .build();

        NavigationUI.setupWithNavController(binding.navView, navController);
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBarConfiguration);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.drawerRecibidos) {
                    navController.navigate(R.id.homeRecibidosFragment);
                } else if (itemId == R.id.drawerFavoritos) {
                    navController.navigate(R.id.favoritosFragment);
                } else if (itemId == R.id.drawerEnviados) {
                    navController.navigate(R.id.enviadosRecyclerMensajeFragment);
                } else if (itemId == R.id.drawerArchivados) {
                    navController.navigate(R.id.archivadosFragment);
                }

                drawerLayout.closeDrawers();
                return true;
            }
        });

        fab = binding.irNuevoMensaje;

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            // Verifica si estás en el fragmento Home
            if (destination.getId() == R.id.homeRecibidosFragment || destination.getId() == R.id.favoritosFragment || destination.getId() == R.id.enviadosRecyclerMensajeFragment || destination.getId() == R.id.archivadosFragment) {
                // Muestra la barra de búsqueda
                MenuItem searchItem = binding.toolbar.getMenu().findItem(R.id.options1Fragment);
                if (searchItem != null) {
                    searchItem.setVisible(true);
                }
                fab.setVisibility(View.VISIBLE);
            } else {
                // Oculta la barra de búsqueda para otros fragmentos
                MenuItem searchItem = binding.toolbar.getMenu().findItem(R.id.options1Fragment);
                if (searchItem != null) {
                    searchItem.setVisible(false);
                }
                fab.setVisibility(View.GONE);
            }
        });

        binding.irNuevoMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_nuevoMensajeFrament);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.options1Fragment) {
            // Navegar al nuevo fragmento (por ejemplo, NuevoFragment)
            navController.navigate(R.id.busqueda);
            return true;
        }

        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);

    }

}
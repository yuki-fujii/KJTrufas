package com.br.kjtrufas;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.br.kjtrufas.sql.DataBase;
import com.br.kjtrufas.suporte.Util;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DataBase dataBase;
    private SQLiteDatabase conn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private boolean conexaoBD()
    {
        try {

            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            return true;
        }
        catch (Exception ex)
        {
            return false;
        }

    }

    public void chamarAdministrador (View view)
    {
        Intent it = new Intent(this, Administrador.class);
        startActivityForResult(it, 0);
    }

    public void chamarRealizarVendas (View view)
    {
        Intent it = new Intent(this, Vendas.class);
        startActivityForResult(it, 0);
    }

    public void chamarConsultarComanda (View view)
    {
        Intent it = new Intent(this, ConsultarComanda.class);
        startActivityForResult(it, 0);
    }

    public void mostrarMeuNome (View view)
    {
        if(conexaoBD())
            Util.integrarVendas(conn);
        else
        {
            android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(this);
            dlg.setMessage("Erro ao conectar-se com o banco de dados.");
            dlg.setNeutralButton("OK", null);
            dlg.show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

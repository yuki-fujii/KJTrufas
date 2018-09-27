package com.br.kjtrufas;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
    FragmentManager fm = getSupportFragmentManager();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState==null)
        {
            VendasFrag vendasFrag = new VendasFrag();

            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.frame_container, vendasFrag, "");
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentTransaction ft = fm.beginTransaction();

        int id = item.getItemId();

        if (id == R.id.nav_produto_disponivel) {

        }

        else if (id == R.id.nav_venda)
        {
            VendasFrag vendasFrag = new VendasFrag();
            ft.replace(R.id.frame_container, vendasFrag, "");
            ft.addToBackStack("pilha");
            ft.commit();
        }
        else if (id == R.id.nav_consulta_pagamento)
        {
            ConsultarComandaFrag consultarComandaFrag = new ConsultarComandaFrag();
            ft.replace(R.id.frame_container, consultarComandaFrag, "");
            ft.addToBackStack("pilha");
            ft.commit();
        }

        else if (id == R.id.nav_configuracao)
        {

        }

        else if (id == R.id.nav_sincronizar)
        {
            if(conexaoBD())
            {
                Util.integrarVendas(conn,this);
            }
            else
            {
                android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(this);
                dlg.setMessage("Erro ao conectar-se com o banco de dados.");
                dlg.setNeutralButton("OK", null);
                dlg.show();
            }
        }

        else if (id == R.id.nav_sair)
        {
            if(conexaoBD())
            {
                DataBase.onDeleteSQLite(conn);
                Intent it = new Intent(this, Login.class);
                startActivityForResult(it, 0);
                finish();
            }
            else
            {
                android.app.AlertDialog.Builder dlg = new android.app.AlertDialog.Builder(this);
                dlg.setMessage("Erro ao fazer logout.");
                dlg.setNeutralButton("OK", null);
                dlg.show();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

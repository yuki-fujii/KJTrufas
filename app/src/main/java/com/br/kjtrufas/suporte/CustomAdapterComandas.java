package com.br.kjtrufas.suporte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.br.kjtrufas.R;

public class CustomAdapterComandas extends BaseAdapter {
    Context context;
    private String[] nome;
    private String[] valor;
    LayoutInflater inflter;

    public CustomAdapterComandas(Context applicationContext, DadosComandas dadosComandas) {
        this.context = applicationContext;
        this.nome = dadosComandas.getNome();
        this.valor = dadosComandas.getValor();
        inflter = (LayoutInflater.from(applicationContext));
    }

    public int getCount() {
        return nome.length;
    }

    public Object getItem(int i) {
        return null;
    }

    public long getItemId(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflter.inflate(R.layout.spinner_comanda, null);

        TextView nomes = view.findViewById(R.id.nomeSpinner);
        TextView valores = view.findViewById(R.id.valorSpinner2);

        nomes.setText(nome[i]);
        valores.setText(valor[i]);
        return view;
    }
}

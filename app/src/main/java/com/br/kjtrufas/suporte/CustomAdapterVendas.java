package com.br.kjtrufas.suporte;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.br.kjtrufas.R;

public class CustomAdapterVendas extends BaseAdapter {
    Context context;
    private String[] data;
    private String[] descricao;
    private String[] valor;
    LayoutInflater inflter;

    public CustomAdapterVendas(Context applicationContext, DadosComanda dadosComanda) {
        this.context = applicationContext;
        this.data = dadosComanda.getData();
        this.descricao = dadosComanda.getDescricao();
        this.valor = dadosComanda.getValor();
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = inflter.inflate(R.layout.spinner_itens_comanda, null);

        TextView datas = view.findViewById(R.id.dataSpinner);
        TextView descricoes = view.findViewById(R.id.descricaoSpinner);
        TextView valores = view.findViewById(R.id.valorSpinner);

        datas.setText(data[i]);
        descricoes.setText(descricao[i]);
        valores.setText(valor[i]);
        return view;
    }
}


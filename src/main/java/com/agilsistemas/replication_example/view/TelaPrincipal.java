package com.agilsistemas.replication_example.view;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.springframework.stereotype.Component;

import com.agilsistemas.replication_example.model.Item;
import com.agilsistemas.replication_example.repository.local.LocalItemRepository;
import com.agilsistemas.replication_example.repository.remote.RemoteItemRepository;

import net.bytebuddy.utility.RandomString;

import java.awt.*;

/**
 * Essa tela swing serve somente para facilitar algumas operações de teste.
 * 
 * @author YuriFernandes150
 */
@Component
public class TelaPrincipal extends JFrame {

    /*
     * Especificamos ambos os repos aqui.
     */
    private final LocalItemRepository localItemRepository;
    private final RemoteItemRepository remoteItemRepository;

    public TelaPrincipal(LocalItemRepository localItemRepository, RemoteItemRepository remoteItemRepository) {
        this.localItemRepository = localItemRepository;
        this.remoteItemRepository = remoteItemRepository;
        montaTela();
        acoesTela();
    }

    JPanel painelSuperior = new JPanel();
    JPanel painelListas = new JPanel(new BorderLayout());

    JSpinner qtdItem = new JSpinner();

    JButton btOk = new JButton("OK");
    JButton btRefresh = new JButton("Atualizar");

    JList<Item> listaItensBancoLocal = new JList<>(new DefaultListModel<>());
    JList<Item> listaItensBancoRemoto = new JList<>(new DefaultListModel<>());

    JScrollPane scrollListas = new JScrollPane(painelListas);

    /**
     * Configuramos os componentes.
     */
    private void montaTela() {

        this.setSize(new Dimension(500, 500));
        this.setTitle("Adicionar itens");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.getContentPane().setLayout(new BorderLayout());

        qtdItem.setBorder(new TitledBorder(new LineBorder(Color.darkGray, 2), "Inserir itens no banco"));
        qtdItem.setPreferredSize(new Dimension(300, 40));
        qtdItem.setModel(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

        btOk.setPreferredSize(new Dimension(80, 40));
        btRefresh.setPreferredSize(new Dimension(80, 40));

        listaItensBancoLocal.setBorder(new TitledBorder(new LineBorder(Color.darkGray, 2), "Local"));

        listaItensBancoRemoto.setBorder(new TitledBorder(new LineBorder(Color.darkGray, 2), "Remoto"));

        painelSuperior.add(qtdItem);
        painelSuperior.add(btOk);
        painelSuperior.add(btRefresh);

        painelListas.add(listaItensBancoLocal, BorderLayout.WEST);
        painelListas.add(listaItensBancoRemoto, BorderLayout.EAST);

        this.getContentPane().add(painelSuperior, BorderLayout.NORTH);
        this.getContentPane().add(scrollListas, BorderLayout.CENTER);
        preencherListas();

    }

    /**
     * Definimos as ações da tela.
     */
    private void acoesTela() {
        btOk.addActionListener(evt -> {

            for (int i = 1; i <= (Integer) qtdItem.getValue(); i++) {
                Item item = new Item();
                item.setDescricaoItem("Item " + RandomString.make());

                localItemRepository.save(item);
            }
            JOptionPane.showMessageDialog(null, "Os items foram salvos!", "Salvo!", 1);
            preencherListas();

        });

        btRefresh.addActionListener(evt -> {
            preencherListas();
        });
    }

    /**
     * Nesse método buscamos os dados em ambos os bancos, com uma contingência no
     * catch, quando o banco remoto estiver offline. Levando em consideração isso,
     * devemos azer contingências em todas as operações do banco remoto, para caso a
     * conexão não for obtida com sucesso.
     */
    private void preencherListas() {

        DefaultListModel<Item> modelLocal = ((DefaultListModel<Item>) listaItensBancoLocal.getModel());
        modelLocal.removeAllElements();
        localItemRepository.findAll().forEach(item -> {
            modelLocal.addElement(item);
        });

        try {
            DefaultListModel<Item> modelRemoto = ((DefaultListModel<Item>) listaItensBancoRemoto.getModel());
            modelRemoto.removeAllElements();
            remoteItemRepository.findAll().forEach(item -> {
                modelRemoto.addElement(item);
            });
            listaItensBancoRemoto.setEnabled(true);
        } catch (Exception e) {
            Item item = new Item(0, "Banco Remoto Offline");
            ((DefaultListModel<Item>) listaItensBancoRemoto.getModel()).addElement(item);
            listaItensBancoRemoto.setEnabled(false);
        }

    }

}

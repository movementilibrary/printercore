package br.com.dasa.controllers.components;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import br.com.dasa.controllers.listeners.EmpresaListener;
import br.com.dasa.controllers.listeners.ImpressoraListener;
import br.com.dasa.controllers.listeners.UnidadeListener;
import br.com.dasa.dtos.ImpressoraDTO;
import br.com.dasa.helpers.DadosImpressaoHelper;
import br.com.dasa.helpers.ImpressoraHelper;
import br.com.dasa.jsons.EmpresaJson;
import br.com.dasa.jsons.UnidadeJson;
import br.com.dasa.services.DadosImpressaoService;
import br.com.dasa.services.PrinterCoreService;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

@Component
public class BodyComponent {

	private static final Logger log = LoggerFactory.getLogger(BodyComponent.class);

	private final ImpressoraHelper impressoraHelper;
	private final PrinterCoreService printerCoreService;
	private final DadosImpressaoService dadosImpressaoService;

	private final ImpressoraDTO impressoraSelecionada = new ImpressoraDTO();
	private final EmpresaJson empresaSelecionada = new EmpresaJson();
	private final UnidadeJson unidadeSelecionada = new UnidadeJson();
	private List<ImpressoraDTO> listaImpressora = new ArrayList<>();
	private List<EmpresaJson> listaEmpresas = new ArrayList<>();
	private List<UnidadeJson> listaUnidade = new ArrayList<>();
	private DadosImpressaoHelper dadosImpressaoHelper;

	@Autowired
	public BodyComponent(ImpressoraHelper impressoraHelper, PrinterCoreService printerCoreService,
			DadosImpressaoService dadosImpressaoService, DadosImpressaoHelper dadosImpressaoHelper) {
		this.impressoraHelper = impressoraHelper;
		this.printerCoreService = printerCoreService;
		this.dadosImpressaoService = dadosImpressaoService;
		this.dadosImpressaoHelper = dadosImpressaoHelper;
	}

	public VBox getBody() {

		VBox box = new VBox();
		box.getChildren().add(getSelects());
		box.getChildren().add(getSeparator());

		return box;

	}

	private Separator getSeparator() {
		final Separator separator = new Separator();
		separator.setValignment(VPos.CENTER);
		return separator;
	}

	private HBox getSelects() {
		HBox panel = new HBox();
		try {
			setarDadosPreenchidos();
			criarSelects(panel);
			panel.getChildren().add(getButtonSelect());
			panel.setPadding(new Insets(60, 10, 20, 10));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return panel;
	}

	private void criarSelects(HBox panel) {
		panel.getChildren()
				.add(getComponenteSelect(
						getLabelSelect("Impressora"), getSelect(listaImpressora,
								new ImpressoraListener(impressoraSelecionada, listaImpressora), impressoraSelecionada),
						new Insets(0, 0, 0, 0)));

		ChoiceBox selectUnidades = getSelect(listaUnidade, new UnidadeListener(unidadeSelecionada, listaUnidade),
				unidadeSelecionada);

		panel.getChildren()
				.add(getComponenteSelect(
						getLabelSelect("Empresas"), getSelect(listaEmpresas, new EmpresaListener(empresaSelecionada,
								listaEmpresas, listaUnidade, selectUnidades, printerCoreService), empresaSelecionada),
						new Insets(0, 0, 0, 50)));

		setarUnidadeSelecionada(empresaSelecionada);

		if (!StringUtils.isEmpty(unidadeSelecionada.getMnemonico())) {
			selectUnidades.getSelectionModel().select(listaUnidade.indexOf(unidadeSelecionada));
		}

		panel.getChildren()
				.add(getComponenteSelect(getLabelSelect("Unidades"), selectUnidades, new Insets(0, 0, 0, 50)));
	}

	private void setarDadosPreenchidos() {
		iniciarListas();
		setarDadosImpressao();
	}

	private void setarDadosImpressao() {
		boolean dadosValidos = dadosImpressaoHelper.validarDadosImpressoraPreenchidos();
		if (dadosValidos) {
			listaImpressora.stream().filter(i -> i.getNomeRede().equals(dadosImpressaoHelper.getNomeImpressora()))
					.findFirst().ifPresent(i -> BeanUtils.copyProperties(i, impressoraSelecionada));
			listaEmpresas.stream().filter(e -> e.getCod().equals(dadosImpressaoHelper.codEmpresa())).findFirst()
					.ifPresent(e -> {
						BeanUtils.copyProperties(e, empresaSelecionada);
						setarUnidadeSelecionada(e);
					});
		}
	}

	private void iniciarListas() {
		this.listaImpressora.clear();
		this.listaImpressora.addAll(impressoraHelper.getImpressoras().stream().map(s -> new ImpressoraDTO(s, null))
				.collect(Collectors.toList()));
		this.listaEmpresas.clear();
		this.listaEmpresas.addAll(printerCoreService.getEmpresas());
		this.listaUnidade.clear();
	}

	private void setarUnidadeSelecionada(EmpresaJson e) {
		this.listaUnidade.addAll(printerCoreService.getUnidades(e.getCod()));
		this.listaUnidade.stream().filter(u -> u.getMnemonico().equals(dadosImpressaoHelper.codUnidade())).findFirst()
				.ifPresent(u -> BeanUtils.copyProperties(u, unidadeSelecionada));

	}

	private StackPane getButtonSelect() {

		StackPane pane = new StackPane();

		Button button = new Button("Salvar");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				dadosImpressaoService.salvarDadosImpressao(impressoraSelecionada, empresaSelecionada,
						unidadeSelecionada);
			}
		});
		pane.setPadding(new Insets(39, 0, 0, 30));
		pane.getChildren().add(button);
		return pane;
	}

	private Label getLabelSelect(String name) {
		Label label = new Label(name);
		label.setFont(Font.font("Comic Sans", 20));
		label.setPadding(new Insets(0, 5, 10, 0));
		return label;
	}

	private VBox getComponenteSelect(Label label, ChoiceBox select, Insets insets) {
		VBox box = new VBox();
		box.getChildren().add(label);
		box.getChildren().add(select);
		if (insets != null) {
			box.setPadding(insets);
		}
		return box;
	}

	private <E extends Object> ChoiceBox getSelect(List<E> lista, ChangeListener changeListener, E obj) {
		ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(lista));
		cb.setPrefSize(150, 20);
		cb.getSelectionModel().selectedIndexProperty().addListener(changeListener);
		if (obj != null) {
			cb.getSelectionModel().select(lista.indexOf(obj));
		}
		return cb;
	}

}

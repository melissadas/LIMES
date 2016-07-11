package org.aksw.limes.core.gui.view.ml;

import java.util.List;
import java.util.Set;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.aksw.limes.core.gui.controller.ml.MachineLearningController;
import org.aksw.limes.core.gui.view.MainView;
import org.aksw.limes.core.measures.measure.MeasureType;
import org.aksw.limes.core.ml.algorithm.AMLAlgorithm;
import org.aksw.limes.core.ml.algorithm.LearningParameter;
import org.aksw.limes.core.ml.algorithm.MLAlgorithmFactory;
import org.aksw.limes.core.ml.algorithm.MLImplementationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class creates the gui elements for the machine learning according to the parameters of the machine learning algorithm selected.
 *  
 * @author Daniel Obraczka {@literal <} soz11ffe{@literal @}
 *         studserv.uni-leipzig.de{@literal >}
 *
 */
public class MachineLearningView {

    /**
     * the logger for this class
     */
    protected static Logger logger = LoggerFactory.getLogger("LIMES");

    /**
     * array containing all implemented algorithms
     */
    private static final String[] algorithms = { MLAlgorithmFactory.EAGLE, MLAlgorithmFactory.LION, MLAlgorithmFactory.WOMBAT_COMPLETE,
	    MLAlgorithmFactory.WOMBAT_SIMPLE };
    
    /**
     * corresponding controller
     */
    protected MachineLearningController mlController;
    
    /**
     * main view of the LIMES application
     */
    private MainView mainView;
    
    /**
     * button that starts the learning
     */
    private Button learnButton;
    
    /**
     * type of view
     */
    private MLImplementationType type;

    /**
     * default constructor setting the variables and creating the view by calling {@link #createMLAlgorithmsRootPane()}
     * @param mainView main view
     * @param mlController controller
     * @param type implementation type: active, batch, unsupervised
     */
    public MachineLearningView(MainView mainView, MachineLearningController mlController, MLImplementationType type) {
	this.mainView = mainView;
	this.mlController = mlController;
	this.mlController.setMlView(this);
	createMLAlgorithmsRootPane();
	this.type = type;
    }

    /**
     * checks if the MLAlgorithm is implemented for this LearningSetting
     * corresponding to the subclass of MachineLearningView and creates the
     * rootPane accordingly
     */
    public void createMLAlgorithmsRootPane() {
	BorderPane border = new BorderPane();
	HBox content = new HBox();
	GridPane root = new GridPane();

	ObservableList<String> mloptions = FXCollections.observableArrayList();
	for (int i = 0; i < algorithms.length; i++) {
	    if (type == MLImplementationType.SUPERVISED_ACTIVE) {
		try {
		    AMLAlgorithm algorithm = MLAlgorithmFactory.createMLAlgorithm(MLAlgorithmFactory.getAlgorithmType(algorithms[i]),
			    MLAlgorithmFactory.getImplementationType(MLAlgorithmFactory.SUPERVISED_ACTIVE));
		    mloptions.add(algorithm.getName());
		} catch (Exception e1) {
		    // TODO Auto-generated catch block
		}
	    } else if (type == MLImplementationType.SUPERVISED_BATCH) {
		try {
		    AMLAlgorithm algorithm = MLAlgorithmFactory.createMLAlgorithm(MLAlgorithmFactory.getAlgorithmType(algorithms[i]),
			    MLAlgorithmFactory.getImplementationType(MLAlgorithmFactory.SUPERVISED_BATCH));
		    mloptions.add(algorithm.getName());
		} catch (Exception e1) {
		    // TODO Auto-generated catch block
		}
	    } else if (type == MLImplementationType.UNSUPERVISED) {
		try {
		    AMLAlgorithm algorithm = MLAlgorithmFactory.createMLAlgorithm(MLAlgorithmFactory.getAlgorithmType(algorithms[i]),
			    MLAlgorithmFactory.getImplementationType(MLAlgorithmFactory.UNSUPERVISED));
		    mloptions.add(algorithm.getName());
		} catch (Exception e1) {
		    // TODO Auto-generated catch block
		}
	    } else {
		logger.info("Unknown subclass of MachineLearningView");
	    }
	}
	ComboBox<String> mlOptionsChooser = new ComboBox<String>(mloptions);
	mlOptionsChooser.setPromptText("choose algorithm");

	learnButton = new Button("learn");
	learnButton.setDisable(true);
	HBox buttonWrapper = new HBox();
	buttonWrapper.setAlignment(Pos.BASELINE_RIGHT);
	buttonWrapper.setPadding(new Insets(25, 25, 25, 25));
	buttonWrapper.getChildren().addAll(learnButton);
	content.getChildren().add(mlOptionsChooser);
	border.setTop(content);
	border.setBottom(buttonWrapper);
	Scene scene = new Scene(border, 300, 400);
	scene.getStylesheets().add("gui/main.css");

	mlOptionsChooser.setOnAction(e -> {
	    root.getChildren().removeAll(root.getChildren());
	    this.mlController.setMLAlgorithmToModel(mlOptionsChooser.getValue());
	    this.mlController.getMlModel().getMlalgorithm().getMl().setDefaultParameters();
	    showParameters(root, mlOptionsChooser.getValue());
	    border.setCenter(root);
	    learnButton.setDisable(false);
	});

	learnButton.setOnAction(e -> {
	    learnButton.setDisable(true);
		this.mlController.learn(this);
	    });

	Stage stage = new Stage();
	stage.setTitle("LIMES - Machine Learning");
	stage.setScene(scene);
	stage.show();
    }

    /**
     * Takes the gridpane in the BorderPane and adds the gui elements fitting
     * for the algorithm. The value of the according learning parameter gets changed if the value of the gui element is changed.
     *
     * @param root
     * @param algorithm
     * @return the GridPane containing the added Nodes
     */
    private GridPane showParameters(GridPane root, String algorithm) {

	AMLAlgorithm mlalgorithm = this.mlController.getMlModel().getMlalgorithm();
	// mlalgorithm.getMl().setDefaultParameters();
	List<LearningParameter> params = mlalgorithm.getParameters();
	for (int i = 0; i < params.size(); i++) {
	    switch (params.get(i).getClazz().getSimpleName().toString().toLowerCase().trim()) {
	    case "integer":
		addNumberParameterHBox(params.get(i), root, i, true);
		break;
	    case "double":
		addNumberParameterHBox(params.get(i), root, i, false);
		break;
	    case "boolean":
		addBooleanParameterHBox(params.get(i), root, i);
		break;
	    case "measuretype":
		addMeasureTypeParameterHBox(params.get(i), root, i);
		break;
	    default:
		logger.error("Unknown LearningParameter clazz " + params.get(i).getClazz().getSimpleName().toString().toLowerCase().trim() + "!");
		break;
	    }
	}
	root.setHgap(10);
	root.setVgap(10);
	root.setPadding(new Insets(25, 25, 25, 25));
	return root;
    }

    /**
     * creates a {@link javafx.scene.layout.HBox} with the information from the learning parameter if it is a Integer or Double
     * @param param
     * @param root
     * @param position
     * @param integer
     */
    private void addNumberParameterHBox(LearningParameter param, GridPane root, int position, boolean integer) {

	HBox parameterBox = new HBox();
	Slider parameterSlider = new Slider();
	Label parameterText = new Label(param.getName());
	Label parameterLabel = new Label(String.valueOf(param.getValue()));

	root.add(parameterText, 0, position);
	parameterBox.getChildren().add(parameterSlider);
	parameterBox.getChildren().add(parameterLabel);
	root.add(parameterBox, 1, position);

	parameterSlider.setTooltip(new Tooltip(param.getDescription()));
	parameterSlider.setMin(param.getRangeStart());
	parameterSlider.setMax(param.getRangeEnd());
	parameterSlider.setValue(Double.valueOf(parameterLabel.getText()));
	parameterSlider.setShowTickLabels(true);
	parameterSlider.setShowTickMarks(false);
	parameterSlider.setMajorTickUnit(parameterSlider.getMax() / 2);
	parameterSlider.setMinorTickCount((int) param.getRangeStep());
	parameterSlider.setSnapToTicks(false);
	parameterSlider.setBlockIncrement(param.getRangeStep());

	parameterSlider.valueProperty().addListener(new ChangeListener<Number>() {
	    public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
		if (integer) {
		    parameterLabel.setText(String.format("%.0f", new_val));
		    param.setValue(new_val.intValue());
		} else {
		    parameterLabel.setText(String.format("%.2f", new_val));
		    param.setValue(new_val.doubleValue());
		}
	    }
	});
    }
    
    /**
     * creates a {@link javafx.scene.layout.HBox} with the information from the learning parameter if it is a Boolean
     * @param param
     * @param root
     * @param position
     */
    private void addBooleanParameterHBox(LearningParameter param, GridPane root, int position) {
	Label parameterLabel = new Label(param.getName());
	CheckBox parameterCheckBox = new CheckBox();
	parameterCheckBox.setTooltip(new Tooltip(param.getDescription()));
	parameterCheckBox.setSelected(false);
	parameterCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
	    public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
		param.setValue(new_val);
	    }
	});
	root.add(parameterLabel, 0, position);
	root.add(parameterCheckBox, 1, position);
    }

    /**
     * creates a {@link javafx.scene.layout.HBox} with the information from the learning parameter if it is a {@link org.aksw.limes.core.measures.measure.MeasureType}
     * @param param
     * @param root
     * @param position
     */
    @SuppressWarnings("unchecked")
    private void addMeasureTypeParameterHBox(LearningParameter param, GridPane root, int position) {
	Label parameterLabel = new Label(param.getName());
	ListView<MeasureTypeItem> parameterListView = new ListView<>();
	MeasureTypeItem.setParams((Set<String>)param.getValue());
	for (int i = 0; i < MeasureType.values().length; i++) {
	    String measure = MeasureType.values()[i].toString().toLowerCase();
	    MeasureTypeItem item = null;
	    if (((Set<String>) param.getValue()).contains(measure)) {
		item = new MeasureTypeItem(measure, true);
	    } else {
		item = new MeasureTypeItem(measure, false);
	    }
	    parameterListView.getItems().add(item);
	}
	parameterListView.setCellFactory(CheckBoxListCell.forListView(new Callback<MeasureTypeItem, ObservableValue<Boolean>>() {
	    @Override
	    public ObservableValue<Boolean> call(MeasureTypeItem item) {
		return item.onProperty();
	    }
	}));
	parameterListView.setOnMouseClicked(new EventHandler<Event>() {
	    @Override
	    public void handle(Event event) {
		parameterListView.getSelectionModel().getSelectedItem().setOn(!parameterListView.getSelectionModel().getSelectedItem().isOn());
	    }
	});
	root.add(parameterLabel, 0, position);
	root.add(parameterListView, 1, position);

    }

    /**
     * class for the parameterHBox to use for the cell factory of the ListView
     * @author Daniel Obraczka {@literal <} soz11ffe{@literal @}
     *         studserv.uni-leipzig.de{@literal >}
     *
     */
    private static class MeasureTypeItem {
	
	/**
	 * name of the MeasureType
	 */
	private final StringProperty name = new SimpleStringProperty();
	/**
	 * true if it is selected
	 */
	private final BooleanProperty on = new SimpleBooleanProperty();
	/**
	 * the LearningParameters to all MeasureTypeItems
	 */
	private static Set<String> params;

	/**
	 * Constructor sets variables and implements listener on {@link #on} the change {@link #params} according to user input
	 * @param name
	 * @param on
	 */
	private MeasureTypeItem(String name, boolean on) {
	    setName(name);
	    setOn(on);
	    this.on.addListener(new ChangeListener<Boolean>() {
	            @Override
	            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
	        	if(newValue){
	        	    params.add(name);
	        	}else{
	        	    params.remove(name);
	        	}
	            }
	        });
	}
	
	/**
	 * sets params
	 * @param p
	 */
	private static final void setParams(Set<String>p){
	    params = p;
	}

	/**
	 * returns nameProperty
	 * @return
	 */
	private final StringProperty nameProperty() {
	    return this.name;
	}

	/**
	 * returns name as String
	 * @return
	 */
	private final String getName() {
	    return this.nameProperty().get();
	}

	/**
	 * sets name
	 * @param name
	 */
	private final void setName(final String name) {
	    this.nameProperty().set(name);
	}

	/**
	 * returns on
	 * @return
	 */
	private final BooleanProperty onProperty() {
	    return this.on;
	}

	/**
	 * returns value of on
	 * @return
	 */
	private final boolean isOn() {
	    return this.onProperty().get();
	}

	/**
	 * sets on
	 * @param on
	 */
	private final void setOn(final boolean on) {
	    this.onProperty().set(on);
	}

	/**
	 * returns name as String
	 */
	@Override
	public String toString() {
	    return getName();
	}

    }

    /**
     * Shows if an Error occurred
     *
     * @param header
     *            Caption of the Error
     * @param content
     *            Error Message
     */
    public void showErrorDialog(String header, String content) {
	Alert alert = new Alert(AlertType.ERROR);
	alert.setHeaderText(header);
	alert.setContentText(content);
	alert.showAndWait();
    }

    /**
     * returns learn button
     * @return learnButton
     */
    public Button getLearnButton() {
	return learnButton;
    }

    /**
     * returns mainView
     * @return mainView
     */
    public MainView getMainView() {
	return mainView;
    }

}

package ie.tcd.pavel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.AbstractSeries;
import com.vaadin.flow.component.charts.model.AxisTitle;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.Cursor;
import com.vaadin.flow.component.charts.model.DataLabels;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.PlotOptionsBar;
import com.vaadin.flow.component.charts.model.PlotOptionsPie;
import com.vaadin.flow.component.charts.model.Tooltip;
import com.vaadin.flow.component.charts.model.VerticalAlign;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import ie.tcd.pavel.documents.*;
import ie.tcd.pavel.security.SecurityUtils;
import ie.tcd.pavel.utility.ExerciseTypes;

import javax.management.Notification;

public class ExerciseChartsPage extends VerticalLayout {

	ExerciseTypes exerciseTypes;
    MongoDBOperations database;
    VerticalLayout verticalLayout= new  VerticalLayout();
	HorizontalLayout horizontalLayoutUIs = new HorizontalLayout();
    public ExerciseChartsPage() {
		this.setWidthFull();
		this.setAlignItems(Alignment.CENTER);
		this.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
    	horizontalLayoutUIs.setWidthFull();
    	database = BeanUtil.getBean(MongoDBOperations.class);
        exerciseTypes = BeanUtil.getBean(ExerciseTypes.class);
		verticalLayout.setWidthFull();
        String[] exercises = exerciseTypes.getExerciseTypes();  
    	List<Group> groups= database.getGroupsByUser(SecurityUtils.getUsername());
    	User currentUser= database.getUserByLogin(SecurityUtils.getUsername());
    	
    	String[] groupsString= new String[groups.size()];
    	for(int i= 0; i<groups.size(); i++)
    	{
    		groupsString[i]= groups.get(i).getName();
    	}
    	
    	ComboBox<String> comboBoxGroup= new ComboBox<>();
    	ComboBox<String> comboBoxExercise= new ComboBox<>();
    	comboBoxGroup.setLabel("Group");
    	comboBoxGroup.setItems(groupsString);
    	comboBoxGroup.addValueChangeListener(groupEvent ->{
    		    		
        	comboBoxExercise.setLabel("Exercise");
        	comboBoxExercise.setItems(exercises);
        	comboBoxExercise.addValueChangeListener(exerciseEvent ->{
				HashMap<String, Double> data= database.inGroupGetCumulativeValuesByUserAndType(comboBoxGroup.getValue(),
						comboBoxExercise.getValue());
        		Chart pieChart = new Chart(ChartType.PIE);
        		pieChart.setWidthFull();
        		Configuration pieChartConfig= pieChart.getConfiguration();
        		pieChartConfig.setTitle("Member Contribution");
        		pieChartConfig.setSubTitle(comboBoxExercise.getValue());
        		
        		Grid<Exercise> grid= new Grid<>(Exercise.class);
        		grid.setWidthFull();
            	grid.setColumns("owner","type", "normalInfo","normalDate");
        		
        		Tooltip tooltip = new Tooltip();
                tooltip.setValueSuffix(getCaption(comboBoxExercise.getValue()));
                pieChartConfig.setTooltip(tooltip);
                
                PlotOptionsPie plotOptions = new PlotOptionsPie();
                plotOptions.setAllowPointSelect(true);
                plotOptions.setCursor(Cursor.POINTER);
                plotOptions.setShowInLegend(true);
                pieChartConfig.setPlotOptions(plotOptions);
                
                DataSeries dataSeries = new DataSeries();
                dataSeries.setName("Score");
                ArrayList<User> usersList = (ArrayList<User>) database.getUsersByGroup(comboBoxGroup.getValue());
                Date date= new Date();
                List<Exercise> allExercise = new ArrayList<Exercise>();
                for(int i = 0; i<usersList.size(); i++)
				{
                	User user= usersList.get(i);
                	dataSeries.add(new DataSeriesItem(user.getLogin(), data.get(user.getLogin())));
                	allExercise.addAll(database.getExercisesByOwnerAndDateInterval(usersList.get(i).getLogin(),
                			new Date(date.getTime()-Long.valueOf("31536000000")), date));
                }
				grid.setItems(allExercise);
                pieChartConfig.setSeries(dataSeries);
                pieChart.setVisibilityTogglingDisabled(true);
				verticalLayout.removeAll();
				verticalLayout.add(pieChart);
				verticalLayout.add(grid);
        	});
			horizontalLayoutUIs.add(comboBoxExercise);
    	});
    	remove(comboBoxExercise);
		horizontalLayoutUIs.add(comboBoxGroup);
		horizontalLayoutUIs.setWidthFull();
		add(horizontalLayoutUIs);
    	add(verticalLayout);
    }
    
    public String getCaption(String type)
    {
        if(exerciseTypes.getDistanceExercises().contains(type))
        {
            return " meters";
        }
        else if(exerciseTypes.getRepExercises().contains(type))
        {
        	return " reps";
        }
        else if(exerciseTypes.getTimeExercises().contains(type))
        {
        	return " minutes";
        }
        else if(exerciseTypes.getWeightExercises().contains(type))
        {
        	return " kg";
        }
        else return "";
    }
}
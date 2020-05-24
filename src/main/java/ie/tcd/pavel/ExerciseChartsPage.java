package ie.tcd.pavel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.vaadin.flow.component.Text;
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
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import ie.tcd.pavel.documents.*;
import ie.tcd.pavel.security.SecurityUtils;
import ie.tcd.pavel.utility.ExerciseTypes;

import javax.management.Notification;

@Route("charts")
public class ExerciseChartsPage extends VerticalLayout {

	ExerciseTypes exerciseTypes;
    MongoDBOperations database;
    VerticalLayout verticalLayout= new VerticalLayout();
    HorizontalLayout horizontalLayout= new HorizontalLayout();
	
    public ExerciseChartsPage() {
    	database = BeanUtil.getBean(MongoDBOperations.class);
        exerciseTypes = BeanUtil.getBean(ExerciseTypes.class);

        String[] exercises = exerciseTypes.getExerciseTypes();  
    	List<Group> groups= database.getGroupsByUser(SecurityUtils.getUsername());
    	User currentUser= database.getUserByLogin(SecurityUtils.getUsername());
    	
    	String[] groupsString= new String[groups.size()];
    	for(int i= 0; i<groups.size(); i++)
    	{
    		groupsString[i]= groups.get(i).getName();
    	}
    	
    	ComboBox<String> comboBoxGroup= new ComboBox<>();
    	comboBoxGroup.setLabel("Group");
    	comboBoxGroup.setItems(groupsString);
    	comboBoxGroup.addValueChangeListener(groupEvent ->{
    		    		
    		ComboBox<String> comboBoxExercise= new ComboBox<>();
    		verticalLayout.remove(comboBoxExercise);
        	comboBoxExercise.setLabel("Exercise");
        	comboBoxExercise.setItems(exercises);
        	comboBoxExercise.addValueChangeListener(exerciseEvent ->{
				HashMap<User, Double> data= database.inGroupGetCumulativeValuesByUserAndType(comboBoxGroup.getValue(),
						comboBoxExercise.getValue());


        		Chart pieChart= new Chart(ChartType.PIE);
        		horizontalLayout.remove(pieChart);
        		Configuration pieChartConfig= pieChart.getConfiguration();
        		pieChartConfig.setTitle("Member Contribution");
        		pieChartConfig.setSubTitle(comboBoxExercise.getValue());
        		
        		Tooltip tooltip = new Tooltip();
                tooltip.setValueDecimals(1);
                pieChartConfig.setTooltip(tooltip);
                
                PlotOptionsPie plotOptions = new PlotOptionsPie();
                plotOptions.setAllowPointSelect(true);
                plotOptions.setCursor(Cursor.POINTER);
                plotOptions.setShowInLegend(true);
                pieChartConfig.setPlotOptions(plotOptions);
                
                DataSeries dataSeries= new DataSeries();
                for(int i= 0; i<database.getUsersByGroup(comboBoxGroup.getValue()).size(); i++)
				{
                	User user= database.getUsersByGroup(comboBoxGroup.getValue()).get(i);
                	dataSeries.add(new DataSeriesItem(user.getLogin(), data.get(user)));
                }
                pieChartConfig.setSeries(dataSeries);
                pieChart.setVisibilityTogglingDisabled(true);
                horizontalLayout.add(pieChart);
        	});
        	verticalLayout.add(comboBoxExercise);
    	});
    	verticalLayout.add(comboBoxGroup);
    	horizontalLayout.add(verticalLayout);
    	add(verticalLayout, horizontalLayout);
    }
}
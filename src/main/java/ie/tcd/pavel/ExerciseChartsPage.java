package ie.tcd.pavel;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.AxisTitle;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataLabels;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.PlotOptionsBar;
import com.vaadin.flow.component.charts.model.PlotOptionsPie;
import com.vaadin.flow.component.charts.model.Tooltip;
import com.vaadin.flow.component.charts.model.VerticalAlign;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.charts.model.YAxis;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("charts")
public class ExerciseChartsPage extends VerticalLayout {

	private String currentExercise= "";
	private int groupSize= 6;
	
    public ExerciseChartsPage() {

    	String[] exercises= new String[] {"Running", "Swimming", "Plank", "Bench Press", "Push Ups", "Lunges", "Weighted Squats", "Squats", "Overhead Dumbbell Press",
                "Dumbbell Rows", "Deadlift", "Burpees", "Sit Ups", "Skipping", "Cycling", "Pull Ups"};   
    	int[] dataMember1= new int[] {200, 300, 22, 4, 400, 340, 6};
    	int[] dataMember2= new int[] {0, 30, 20, 1, 40, 170, 260};
    	int[] dataMember3= new int[] {100, 0, 20, 340, 200, 300, 57};
    	int[] dataMember4= new int[] {400, 3, 20, 23, 73, 250, 168};
    	int[] dataMember5= new int[] {150, 100, 30, 73, 45, 27, 221};
    	int[] dataMember6= new int[] {70, 75, 21, 3, 47, 25, 72};
    	String[] daysOfTheWeek= new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    	
    	//Creating a drop down menu of all the exercises
    	ComboBox<String> comboBox= new ComboBox<>();
    	comboBox.setLabel("Exercise");
    	comboBox.setItems(exercises);   	
    	comboBox.addValueChangeListener(event ->{
        	currentExercise= event.getValue();
        });
    	add(comboBox);
    	
    	//Adding bar chart
    	Chart barChart= new Chart();
        Configuration configBarChart= barChart.getConfiguration();
        configBarChart.setTitle("Group Leaderboard");
        configBarChart.setSubTitle(currentExercise);
        barChart.getConfiguration().getChart().setType(ChartType.BAR);
        
        for(int index= 0; index<groupSize; index++)
        {
        	configBarChart.addSeries(new ListSeries(daysOfTheWeek[index], dataMember1[index], dataMember2[index], dataMember3[index], dataMember4[index], dataMember5[index], dataMember6[index]));    
        	
        }
        
        XAxis xAxis= new XAxis();
        xAxis.setCategories("Member 1", "Member 2", "Member 3", "Member 4", "Member 5", "Member 6");
        configBarChart.addxAxis(xAxis);
        
        YAxis yAxis= new YAxis();
        yAxis.setMin(0);
        AxisTitle yTitle= new AxisTitle();
        yTitle.setText("Future parameter");
        yTitle.setAlign(VerticalAlign.HIGH);
        yAxis.setTitle(yTitle);
        configBarChart.addyAxis(yAxis);
        
        Tooltip tooltip = new Tooltip();
        tooltip.setValueSuffix(" future parameter");
        tooltip.setShared(true);
        configBarChart.setTooltip(tooltip);
        
        PlotOptionsBar plotOptions= new PlotOptionsBar();
        DataLabels dataLabels= new DataLabels();
        dataLabels.setEnabled(true);
        plotOptions.setDataLabels(dataLabels);
        configBarChart.setPlotOptions(plotOptions);
        add(barChart);
    }
}

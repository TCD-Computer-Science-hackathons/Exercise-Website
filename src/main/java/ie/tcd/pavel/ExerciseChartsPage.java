package ie.tcd.pavel;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.Configuration;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.ListSeries;
import com.vaadin.flow.component.charts.model.PlotOptionsPie;
import com.vaadin.flow.component.charts.model.XAxis;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

@Route("charts")
public class ExerciseChartsPage extends VerticalLayout {

	private String currentExercise= "";
	private int groupSize= 6;
	
    public ExerciseChartsPage() {
    	String[] exercises= new String[] {"Running", "Swimming", "Plank", "Bench Press", "Push Ups", "Lunges", "Weighted Squats", "Squats", "Overhead Dumbbell Press",
                "Dumbbell Rows", "Deadlift", "Burpees", "Sit Ups", "Skipping", "Cycling", "Pull Ups"};    	
    	
    	SplitLayout layout= new SplitLayout();
    	ComboBox<String> comboBox= new ComboBox<>();
    	comboBox.setLabel("Exercise");
    	comboBox.setItems(exercises);   	
    	layout.addToPrimary(comboBox);
    	comboBox.addValueChangeListener(event ->{
        	currentExercise= event.getValue();
        });
    	add(layout);

    	Chart barChart= new Chart();
        Configuration configBarChart= barChart.getConfiguration();
        configBarChart.setTitle("Group Leaderboard");
        configBarChart.setSubTitle(currentExercise);
        barChart.getConfiguration().getChart().setType(ChartType.BAR);
        XAxis xAxis= new XAxis();
        for(int index= 0; index<groupSize; index++)
        {
        	xAxis.setCategories("Member "+ 1);
        	configBarChart.addSeries(new ListSeries("Member "+ index, 200));
        }
        configBarChart.addxAxis(xAxis);
        
        Chart pieChart= new Chart();
        Configuration configPieChart= pieChart.getConfiguration();
        configPieChart.setTitle("Group Contribution");
        configPieChart.setSubTitle(currentExercise);
        pieChart.getConfiguration().getChart().setType(ChartType.PIE);
        //PlotOptionsPie pieChartOptions= new PlotOptionsPie();
        //pieChartOptions.setInnerSize("20");
        DataSeries series= new DataSeries();
        for(int index= 0; index<groupSize; index++)
        {
        	series.add(new DataSeriesItem("Member "+ index, 3));
        	configPieChart.addSeries(series);
        }
        
    	Tab chartTab= new Tab("Group Leaderboard");
    	Tab pieTab= new Tab("Member Contribution");
    	Tabs tabs= new Tabs(chartTab, pieTab);
    	layout.addToSecondary(tabs);
    }
}

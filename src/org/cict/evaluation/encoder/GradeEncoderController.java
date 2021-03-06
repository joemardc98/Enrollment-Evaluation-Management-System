/**
 * CAPSTONE PROJECT.
 * BSIT 4A-G1.
 * MONOSYNC TECHNOLOGIES.
 * MONOSYNC FRAMEWORK VERSION 1.0.0 TEACUP RICE ROLL.
 * THIS PROJECT IS PROPRIETARY AND CONFIDENTIAL ANY PART THEREOF.
 * COPYING AND DISTRIBUTION WITHOUT PERMISSION ARE NOT ALLOWED.
 *
 * COLLEGE OF INFORMATION AND COMMUNICATIONS TECHNOLOGY.
 * LINKED SYSTEM.
 *
 * PROJECT MANAGER: JHON MELVIN N. PERELLO
 * DEVELOPERS:
 * JOEMAR N. DE LA CRUZ
 * GRETHEL EINSTEIN BERNARDINO
 *
 * OTHER LIBRARIES THAT ARE USED BELONGS TO THEIR RESPECTFUL OWNERS AND AUTHORS.
 * NO COPYRIGHT ARE INTENTIONAL OR INTENDED.
 * THIS PROJECT IS NOT PROFITABLE HENCE FOR EDUCATIONAL PURPOSES ONLY.
 * THIS PROJECT IS ONLY FOR COMPLIANCE TO OUR REQUIREMENTS.
 * THIS PROJECT DOES NOT INCLUDE DISTRIBUTION FOR OTHER PURPOSES.
 *
 */
package org.cict.evaluation.encoder;

import app.lazy.models.AcademicProgramMapping;
import app.lazy.models.AcademicTermMapping;
import app.lazy.models.CurriculumMapping;
import app.lazy.models.CurriculumSubjectMapping;
import app.lazy.models.DB;
import app.lazy.models.Database;
import app.lazy.models.GradeMapping;
import app.lazy.models.StudentMapping;
import app.lazy.models.StudentProfileMapping;
import app.lazy.models.SubjectMapping;
import artifacts.FTPManager;
import artifacts.ImageUtility;
import com.jfoenix.controls.JFXTreeTableView;
import com.jhmvin.Mono;
import com.jhmvin.fx.async.SimpleTask;
import com.jhmvin.fx.display.ControllerFX;
import com.jhmvin.fx.display.SceneFX;
import com.jhmvin.transitions.Animate;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.apache.commons.io.FileUtils;
import org.cict.evaluation.encoder.view.GradeEncoderUI;
import org.cict.evaluation.encoder.view.RatingTableClass;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.spreadsheet.Grid;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetView;
import org.hibernate.criterion.Order;

/**
 *
 * @author Joemar
 */
public final class GradeEncoderController extends SceneFX implements ControllerFX {

    @FXML
    private VBox pnl_main;

    @FXML
    private Label lbl_lastname;

    @FXML
    private Label lbl_firstname;

    @FXML
    private Label lbl_middlename;

    @FXML
    private Label lbl_course;

    @FXML
    private Label lbl_stud_id;

    @FXML
    private JFXTreeTableView<RatingTableClass> tbl_rating;

    @FXML
    private HBox pnl_spreadsheet;
    
    @FXML
    private HBox pnl_loading;
    
    @FXML
    private HBox pnl_error;

    @FXML
    private Label lblTitle;

    @FXML
    private Button btnSkip;

    @FXML
    private Button btnPost;
    
    @FXML
    private ImageView img_profile;

    private GradeEncoderUI gei;
    private SpreadsheetView spv;
    private StudentMapping CURRENT_STUDENT;
    private ArrayList<SubjectMapping> subjectsToEncode;
    private String TITLE;
    private String MODE;
    private Integer CURRICULUM_id, yearLevel, semester;

    public GradeEncoderController(String mode, StudentMapping student, ArrayList<SubjectMapping> subjectsToEncode, String title) {
        this.CURRENT_STUDENT = student;
        this.subjectsToEncode = subjectsToEncode;
        this.TITLE = title;
        this.MODE = mode;
        try {
            this.gei = new GradeEncoderUI();
        } catch (Exception e) {
            System.err.println("Error in constructing spreadsheet");
            Notifications.create().title("Oh no! Something went wrong.")
                    .text("Subjects are not loaded properly.\n"
                            + "Please try again later.")
                    .showError();
        }
    }

    public void setYearAndSem(Integer yr, Integer sem) {
        this.yearLevel = yr;
        this.semester = sem;
    }

    public GradeEncoderController() {
        // blank constructor
    }

    private Pane application_root;

    @Override
    public void onInitialization() {
        this.application_root = this.pnl_main;
        super.bindScene(application_root);
        this.init();
        this.onPrepare();
    }

    private void init() {
        //-------------------------------
        // set image
        SimpleTask set_profile = new SimpleTask("set_profile");
        set_profile.setTask(()->{
            this.setImageView();
        });
        set_profile.whenCancelled(()->{
            Notifications.create().text("Loading of image is cancelled")
                    .showInformation();
        });
        set_profile.whenFailed(()->{
            Notifications.create().text("Failed to load image.")
                    .showInformation();
        });
        set_profile.whenSuccess(()->{
        });
        set_profile.start();
        //-------------------------------
        
        
        this.changeView(pnl_loading);
        this.CURRICULUM_id = CURRENT_STUDENT.getCURRICULUM_id();
        this.tbl_rating = this.gei.createGradeTable(this.tbl_rating);
        this.gei.setNotificationPane(pnl_main);
        btnPost.setDisable(false);
        try {
            gei.setAcadTermId(this.getAcadTermId());
        } catch (NullPointerException a) {
        }
        gei.setCictId(this.CURRENT_STUDENT.getCict_id());
        gei.setSubjectsToBePrinted(this.subjectsToEncode);
        gei.setMode(this.MODE);
        gei.setCurriculumID(CURRICULUM_id, yearLevel, semester);
        SimpleTask createSpredSheetTx = new SimpleTask("create-ss");

        createSpredSheetTx.setTask(() -> {
            createSpreadSheet();
        });
        createSpredSheetTx.whenStarted(() -> {
            tbl_rating.setDisable(true);
            btnPost.setDisable(true);
            super.cursorWait();
        });
        createSpredSheetTx.whenCancelled(() -> {
            // cancelled is called upon error.
            this.changeView(pnl_error);
            super.cursorDefault();
        });
        createSpredSheetTx.whenFailed(() -> {
            super.cursorDefault();
            this.changeView(pnl_error);
            Notifications.create().title("Oh no! Something went wrong.")
                    .text("Subjects are not loaded properly.\n"
                            + "Please try again later.")
                    .showError();
        });
        createSpredSheetTx.whenSuccess(() -> {
            pnl_spreadsheet.getChildren().clear();
            pnl_spreadsheet.getChildren().add(spv);
            //------------------------------------------------------------------
            // this will decide if the POST button will be enabled.
            this.writeEncodedGrades();
            //------------------------------------------------------------------
            super.cursorDefault();
            this.changeView(pnl_spreadsheet);
        });
        createSpredSheetTx.whenFinished(() -> {
            tbl_rating.setDisable(false);
            //------------------------------------------------------------------
            if (this.spv.getGrid().getRowCount() == 0 || this.spv.getGrid() == null) {
                Mono.fx().alert().createWarning()
                        .setTitle("Close")
                        .setHeader("Empty List")
                        .setMessage("Sorry but there are no grades to be encoded.")
                        .showAndWait();
                super.close();
            }
        });
        createSpredSheetTx.start();
    }

    @Override
    public void onEventHandling() {
        this.buttonEvents();
    }

    private void buttonEvents() {
        btnPost.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            int choice = Mono.fx()
                    .alert()
                    .createConfirmation()
                    .setTitle("Encode Grade")
                    .setHeader("Confirmation")
                    .setMessage("Are you sure you want to post grades? "
                            + "This will no longer be editable after this process.")
                    .confirmCustom("Yes", "No");

            if (choice == 1) {
                this.gei.pnl_spreadsheet = this.pnl_spreadsheet;
                this.gei.verifySheet(btnPost);
            }
        });

        this.addClickEvent(btnSkip, () -> {
            int choice = 0;
            if (!btnPost.isDisable()) {
                choice = Mono.fx()
                        .alert()
                        .createConfirmation()
                        .setHeader("Skip")
                        .setMessage("Are you sure you want to skip posting grades?")
                        .confirmYesNo();
            } else {
                choice = 1;
            }
            if (choice == 1) {
                Mono.fx().getParentStage(btnPost).close();
            }
        });
    }

    public void createSpreadSheet() {
        spv = this.gei.createSpreadsheet();
        HBox.setHgrow(spv, Priority.ALWAYS);
    }

    private void logs(String str) {
        if (false) {
            System.out.println("@GradeEncoderController: " + str);
        }
    }

    private void writeEncodedGrades() {
        // enable the post button
        this.btnPost.setDisable(false);
        // disable first value filtering to write notes
        this.gei.setValueFiltering(false);
        // starts here
        int writeCount = 0;
        ArrayList<SubjectMapping> subjects = this.subjectsToEncode;
        Grid sheetyGrid = this.spv.getGrid();
        for (ObservableList<SpreadsheetCell> row : sheetyGrid.getRows()) {
            // iterate to all the row of the current spreadsheet
            String subjectCode = row.get(0).getText(); // first column is subject code
            // now lets get its subject id
            Integer subID = this.isInList(subjects, subjectCode);
            // check if null (not existing)
            if (subID != null) {
                // now you have the gradeRating
                String gradeRating = this.getGradeRating(subID);
                // let's write it in the spreadsheet if there is a grade
                if (gradeRating != null) {
                    this.writeSheetValues(row.get(3), gradeRating);
                    //----------------------------------------------------------
                    // after writing test the value
                    String remarks = row.get(4).getText();
                    boolean isPassed = remarks.equalsIgnoreCase("PASSED");
                    if (isPassed) {
                        // only passed remarks are counted
                        writeCount++;
                    } else {
                        // make the cell updatable
                        row.get(3).setEditable(true);
                        
                        // check if the remarks is failed or inc
                        if(remarks.equalsIgnoreCase("FAILED") || remarks.equalsIgnoreCase("INCOMPLETE")) {
                            // if these fields are modified, ask for 
                            // local registrar's credentials
                            // before saving grades
                            HashMap<String, String> detail = new HashMap();
                            detail.put(row.get(0).getText(), remarks);
                            gei.addRestrictedSubject(detail);
                        }
                    }

                    //----------------------------------------------------------
                }
            }
        }

        this.gei.setValueFiltering(true);
        //----------------------------------------------------------------------
        // now when the loop is over let's compare how many rows we have against writeCount
        // this will decide if the post button will be disabled
        if (sheetyGrid.getRows().size() == writeCount) {
            System.out.println("ROW SIZE: " + sheetyGrid.getRows().size());
            // if they are equal grades are complete
            this.btnPost.setDisable(true); // disable the post button
            // re enable value filtering

        }

    }

    private Integer isInList(ArrayList<SubjectMapping> list, String subjectCode) {
        for (SubjectMapping subjectMapping : list) {
            if (subjectCode.equalsIgnoreCase(subjectMapping.getCode())) {
                return subjectMapping.getId();
            }
        }
        return null; // not in the list
    }

    /**
     * Writes a value to a specific cell and makes it un-editable
     *
     * @param row row from spreadsheet
     * @param col col from spreadsheet
     * @param value assign values (GRADE)
     */
    private void writeSheetValues(SpreadsheetCell currentCell, String value) {
        currentCell.setItem(value);
        currentCell.setEditable(false);
    }

    public String getGradeRating(Integer subjectID) {
        // since subject id is unique it will return only 1 result
        // unless the subject is taken twice or more
        // just pickup the latest
        GradeMapping grades = Mono.orm()
                .newSearch(Database.connect().grade())
                .eq("STUDENT_id", this.CURRENT_STUDENT.getCict_id())
                .eq("SUBJECT_id", subjectID)
                .active(Order.desc(DB.grade().id))
                .first();
        // if not empty return the first latest result
        return grades == null ? null : grades.getRating();
    }

    public boolean onPrepare() {
        try {
            lblTitle.setText(this.TITLE);
            lbl_stud_id.setText(CURRENT_STUDENT.getId());
            lbl_firstname.setText(CURRENT_STUDENT.getFirst_name());
            lbl_lastname.setText(CURRENT_STUDENT.getLast_name());
            lbl_middlename.setText(CURRENT_STUDENT.getMiddle_name() == null ? "" : CURRENT_STUDENT.getMiddle_name());

            CurriculumMapping curriculum
                    = Database.connect()
                            .curriculum()
                            .getPrimary(this.CURRENT_STUDENT.getCURRICULUM_id());

            AcademicProgramMapping aMap
                    = Database.connect().academic_program()
                            .getPrimary(curriculum.getACADPROG_id());

            lbl_course.setText(aMap.getCode());
        } catch (NullPointerException e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    private Integer getAcadTermId() {
        Integer admissionYear = null;
        try {
            SubjectMapping subjectReference = this.subjectsToEncode.get(0);
            CurriculumSubjectMapping curriculumSubjectReference = Mono.orm()
                    .newSearch(Database.connect().curriculum_subject())
                    .eq("SUBJECT_id", subjectReference.getId())
                    .active()
                    .first();
            Integer subjectYear = curriculumSubjectReference.getYear();
            Integer semester = curriculumSubjectReference.getSemester();
            try {
                admissionYear = Integer.valueOf(this.CURRENT_STUDENT.getAdmission_year());
            } catch (NumberFormatException a) {
                logs("NumberFormatException ADMISSION YEAR");
            }
            admissionYear += (subjectYear - 1);
            String school_year = admissionYear + "-" + (admissionYear += 1);
            logs(school_year);
            AcademicTermMapping acadTerm = Mono.orm()
                    .newSearch(Database.connect().academic_term())
                    .eq("school_year", school_year)
                    .eq("semester_regular", semester)
                    .active()
                    .first();
            return acadTerm.getId();
        } catch (IndexOutOfBoundsException a) {
            return null;
        }
    }
    
    //-------------------------
    private void changeView(Node node) {
        Animate.fade(node, 150, ()->{
            pnl_error.setVisible(false);
            pnl_loading.setVisible(false);
            pnl_spreadsheet.setVisible(false);
            node.setVisible(true);
        }, pnl_error, pnl_loading, pnl_spreadsheet);
    }
    
    private void setImageView() {
        StudentProfileMapping spMap = null;
        if(this.CURRENT_STUDENT.getHas_profile().equals(1)) {
            spMap = Mono.orm().newSearch(Database.connect().student_profile())
                    .eq(DB.student_profile().STUDENT_id, this.CURRENT_STUDENT.getCict_id())
                    .active(Order.desc(DB.student_profile().id)).first();
        }
        String studentImage = (spMap==null? null: spMap.getProfile_picture());
        if (studentImage == null
            || studentImage.isEmpty()
            || studentImage.equalsIgnoreCase("NONE")) {
            ImageUtility.addDefaultImageToFx(img_profile, 1);
        } else {
            ImageUtility.addImageToFX("temp/images/profile", "student_avatar", studentImage, img_profile, 1);
        }
    }
}

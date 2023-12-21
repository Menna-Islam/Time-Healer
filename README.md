# Time-Healer
Stand-alone application represents a clinic management system 
## GUI 
The entry point of our application is represented by the login page, which is implemented in the  <a href="https://github.com/Menna-Islam/Time-Healer/blob/main/src/GUI.java">GUI.java</a> file.

 Upon selecting the user role, there are three possible scenarios: 
1. entering wrong credentials 

![My_Image](invalid_credentials.jpg)

2. leaving on of the fields empty

![My_Image](complete_info.jpg)

3. entering valid credentials , navigating us to the page of selected role 

## Admin page 
The administrator page, implemented in the  <a href="https://github.com/Menna-Islam/Time-Healer/blob/main/src/AdminPanel.java">AdminPanel.java</a> file , encompasses all necessary functionalities.

in the admin page 

![My_Image](admin_panel.jpg)

When clicking the "Manage Database" button, it leads to the following page:

![My_Image](accounts.jpg)

### Add account
Selecting the "Add Account" button

![My_Image](adding_account.jpg)

allows the creation of user accounts by specifying the role and entering credentials.
The system handles scenarios for both existing and new users.

Existing User Credentials:

![My_Image](entering_credentails_to_existing_user.jpg)

New User Credentials:

![My_Image](adding_new_user.jpg)

and then it goes back to the admin page 


### Edit account
Choosing the "Edit Account" button
first , it requires choosing the role of account to modify and credentials for security 

![My_Image](edit_account_page.jpg)

then,It navigates us to the editing page , requiring to choose what field exactly do you want to modify 

![My_Image](edit_account.jpg)

![My_Image](modifying_password.jpg)

![My_Image](modifying_both.jpg)

after entering the new data : 

![My_Image](modifying_result.jpg)

and then it goes back to the admin page 


### Delete account
The "Delete Account" button prompts the selection of the role and entry of credentials for the targeted account.

![My_Image](delete_account.jpg)

After deletion:

![My_Image](deleting_result.jpg)

and then it goes back to the admin page 


## Doctor page 
The doctor page , found in found in <a href="https://github.com/Menna-Islam/Time-Healer/blob/main/src/DoctorPanel.java">DoctorPanel.java</a> file 

provides functionalities for viewing appointments, viewing medical records, and managing medical records.

![My_Image](doctor_panel.jpg)

### View appointments 
Doctors can view and verify all scheduled appointments.

![My_Image](view_appointments.jpg)


### View medical record 
Doctors can access patient medical records by selecting a specific patient.

![My_Image](view_medical_reocrds.jpg)

![My_image](choosing_patient's_medical_record.jpg)

### Manage medical record

In addition to viewing records, doctors have the capability to modify medical records.

![My_Image](editing_record.jpg)

After modification:
 
![My_Image](editing_record_results.jpg)


## Receptionist page 

The receptionist page, implemented in <a href="https://github.com/Menna-Islam/Time-Healer/blob/main/src/ReceptionistPanel.java">ReceptionistPanel.java</a> file encompasses functionalities for managing patients and scheduling appointments.

![My_Image](receptionist_panel.jpg)

### Manage patients 
By clicking the "Manage Patients" button , it navigates us to the page that shows us all patients registered

![My_Image](manage_patients.jpg)

you have a list of decisions that receptionist can make : 
- add new patient
- create medical record for a patient (has to be existing patient)
- delete patient

#### Adding new patient 
Receptionists can add new patients, filling in necessary details.

![My_Image](add_patient_form.jpg)

Upon completion:

![My_Image](adding_patient_message.jpg)  

and this is the patients sheet after adding the patient

![My_Image](adding_patient_result.jpg) 

#### Creating medical record 

After selecting an existing patient, receptionists can create medical records.
 
![My_Image](create_medical_record.jpg) 

![My_Image](create_medical_record_result.jpg) 

 
### Schedule appointments
Receptionists can view, add, or delete appointments.

![My_Image](schedule.appointments.jpg) 


#### Show and delete appointments 

Receptionists can delete appointments by selecting and confirming the deletion.

![My_Image](show_and_delete_appointment.jpg) 


#### Add appointments 

Receptionists can add appointments and  generating a bill for the booked appointment.

![My_Image](add_appointment.jpg) 

Resulting in:

![My_Image](bill.jpg) 


























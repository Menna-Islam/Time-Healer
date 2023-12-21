# Time-Healer
Stand-alone application represents a clinic management system 
## GUI 
that class represents the entry point of our application , represtenting the login page , found in <a href="https://github.com/Menna-Islam/Time-Healer/blob/main/src/GUI.java">GUI.java</a> file.

after choosing the role of user there are three cases 
first : entering wrong credentials 

![My_Image](invalid_credentials.jpg)

second : leaving on of the fields empty

![My_Image](complete_info.jpg)

third : entering valid credentials , navigating us to the page of selected role 

## Admin page 
that class represents administartor page with all the functionalities required , found in  found in <a href=" https://github.com/Menna-Islam/Time-Healer/blob/main/src/com/clinic/AdminPanel.java">AdminPanel.java</a>

in the admin page 

![My_Image](admin_panel.jpg)

when we press "manage data base button"
It navigates us to this page

![My_Image](accounts.jpg)

### Add account
by choosing "add account" button

![My_Image](adding_account.jpg)

you can choose the role of user to create an account for , then enter credentials 

if you enter credentials for an existing user 

![My_Image](entering_credentails_to_existing_user.jpg)

if you enter credentials for a new user 

![My_Image](adding_new_user.jpg)

and then it goes back to the admin page 


### Edit account
by choosing "edit account" button
first , it requires choosing the role of account to modify and credentials for security 

![My_Image](edit_account_page.jpg)

then,It navigates us to the editing page , requiring to choose what field exactly do you want to modify 

![My_Image](edit_account.jpg)

![My_Image](modifying_passwordt.jpg)

![My_Image](modifying_both.jpg)

after entering the new data 

![My_Image](modifying_result.jpg)

and then it goes back to the admin page 


### Delete account
by choosing "delete account" button
as we frequently said , requirig choosing the role and credentials to the account you want to delete 

![My_Image](delete_account.jpg)

then

![My_Image](deleting_result.jpg)

and then it goes back to the admin page 


## Doctor page 
that class represents doctor page with all the functionalities required , found in  found in <a href=" https://github.com/Menna-Islam/Time-Healer/blob/main/src/com/clinic/DoctorPanel.java">DoctorPanel.java</a>

in doctor page 

![My_Image](doctor_panel.jpg)

### View appointments 
doctor can see and check all the appointments booked 

![My_Image](view_appointments.jpg)


### View medical record 
doctor also can view medical records of patients 
but first , you have to choose the patient

![My_Image](view_medical_reocrds.jpg)

![My_image](choosing_patient's_medical_record.jpg)

### Manage medical record

same as viewing record you have to choose role first 
but here , you have the ability to modify medical record

![My_Image](editing_record.jpg)

then 
![My_Image](editing_record_results.jpg)


## Receptionist page 

that class represents receptionist page with all the functionalities required , found in  found in <a href=" https://github.com/Menna-Islam/Time-Healer/blob/main/src/com/clinic/ReceptionistPanel.java">ReceptionistPanel.java</a>

in doctor page 

![My_Image](receptionist_panel.jpg)

### Manage patients 
by pressing "manage patients" button , it navigates us to the page that shows us all patients registered

![My_Image](manage_patients.jpg)

you have a list of decisions that receptionist can make : 
- add new patient
- create medical record for a patient (has to be existing patient)
- delete patient

#### Adding new patient 

![My_Image](add_patient_form.jpg)

then 

![My_Image](adding_patient_message.jpg)  

and this is the patients sheet after adding the patient

![My_Image](adding_patient_result.jpg) 

#### Creating medical record 

patient should be extisting to create medical record for them 
 first , we choose patient 
 then , we create medical record 
 
![My_Image](create_medical_record.jpg) 

![My_Image](create_medical_record_result.jpg) 

 
### Schedule appointments
receptionist can see , add or delete appointments

![My_Image](schedule.appointments.jpg) 


#### Show and delete appointments 

receptionist can delete appointment by selecting appointment then delete it 

![My_Image](show_and_delete_appointment.jpg) 


#### Add appointments 

receptionist can add appointment 

![My_Image](add_appointment.jpg) 

and then it displays the bill for booked appointment 

![My_Image](bill.jpg) 


























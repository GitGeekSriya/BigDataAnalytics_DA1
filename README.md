# Student Performance MapReduce

## Overview

This repository contains a MapReduce implementation designed to process student performance data. The program reads data from multiple text files, filters students based on their year of admission, and computes the highest marks obtained in a specific course (CSE2035) across two assessment categories (CATI and CAT2).

## Problem Statement

Develop a MapReduce program to:

1. **Filter students** who were admitted in a user-specified year.
2. **Determine the highest mark** achieved in the CSE2035 course across two assessments (CATI and CAT2) for each student.
3. **Output** the student ID, name, and the highest mark obtained for each student.

### Input Files

1. **Stud.txt**: Contains student details.
   - **Columns**: `std_id`, `std_name`, `mobile_number`, `address`, `year_of_admission`
   
2. **Catl.txt**: Contains CATI marks for the CSE2035 course.
   - **Columns**: `std_id`, `cat1_mark`
   
3. **Cat2.txt**: Contains CAT2 marks for the CSE2035 course.
   - **Columns**: `std_id`, `cat2_mark`

### Output

The output will include:
- `std_id`: Student ID
- `std_name`: Student Name
- `highest_mark`: Highest mark obtained in either CATI or CAT2 for the specified year of admission

## Code Explanation

### Mapper Classes

1. **`stud` Mapper Class**

   - **Purpose:** Processes the student details file (`Stud.txt`).
   - **Inputs:** A line from the student details file.
   - **Outputs:** 
     - Key: `std_id`
     - Value: `"stud,student_name"`
   - **Logic:** 
     - Retrieves the year of admission from configuration.
     - Splits the input line to extract `std_id`, `student_name`, and year of admission.
     - Emits the student ID and name if the year matches the specified year.

2. **`cat1` Mapper Class**

   - **Purpose:** Processes the CATI marks file (`Catl.txt`).
   - **Inputs:** A line from the CATI marks file.
   - **Outputs:** 
     - Key: `std_id`
     - Value: `"cat1,cat1_mark"`
   - **Logic:** 
     - Splits the input line to extract `std_id` and `cat1_mark`.
     - Emits the student ID and CATI mark.

3. **`cat2` Mapper Class**

   - **Purpose:** Processes the CAT2 marks file (`Cat2.txt`).
   - **Inputs:** A line from the CAT2 marks file.
   - **Outputs:** 
     - Key: `std_id`
     - Value: `"cat2,cat2_mark"`
   - **Logic:** 
     - Splits the input line to extract `std_id` and `cat2_mark`.
     - Emits the student ID and CAT2 mark.

### Reducer Class

**`jreducer` Reducer Class**

- **Purpose:** Aggregates and processes the data emitted by the Mapper classes.
- **Inputs:** 
  - Key: `std_id`
  - Values: List of values related to `std_id` (e.g., `"stud,student_name"`, `"cat1,cat1_mark"`, `"cat2,cat2_mark"`)
- **Outputs:** 
  - Key: `std_id student_name`
  - Value: `highest_mark`
- **Logic:**
  - Initializes variables to track CATI and CAT2 marks.
  - Iterates through the list of values for each `std_id`:
    - Extracts and stores student name.
    - Retrieves and stores CATI and CAT2 marks.
  - Determines the maximum mark between CATI and CAT2.
  - Emits the student ID and name along with the highest mark.

### Main Class

**`ro22mis1148` Main Class**

- **Purpose:** Configures and runs the MapReduce job.
- **Configuration:**
  - Sets up the Hadoop configuration and job parameters.
  - Passes the specified year of admission as a configuration parameter.
- **Job Setup:**
  - Specifies Mapper and Reducer classes.
  - Configures input and output paths.
  - Defines output key and value classes.
- **Execution:**
  - Submits the job and waits for completion.
 
    ## How It Works
    ### Steps

1. **Reading Student Data:**

   - **Files Involved:** `Stud.txt`, `Catl.txt`, `Cat2.txt`
   - **Details:**
     - `Stud.txt`: Contains student details including ID, name, and year of admission.
     - `Catl.txt` and `Cat2.txt`: Contain marks for CATI and CAT2 assessments, respectively.
   
   - **Map Phase:**
     - The `stud` Mapper reads `Stud.txt`, splits each line, and emits the student ID along with the student name if the student’s year of admission matches the specified year.
     - The `cat1` Mapper processes `Catl.txt`, emitting student IDs and CATI marks.
     - The `cat2` Mapper processes `Cat2.txt`, emitting student IDs and CAT2 marks.

2. **Applying the Reduction Logic:**

   - **Reducer Class:** `jreducer`
   - **Functionality:**
     - Aggregates data by student ID.
     - For each student, it processes the emitted data to find the maximum score between CATI and CAT2.
     - Combines the student name with the highest score obtained from the two assessments.
   
   - **Output:**
     - The student ID, student name, and the highest mark achieved are written to the output.

3. **Processing the Data:**

   - The program utilizes a MapReduce job to:
     - Filter students by the year of admission.
     - Compare CATI and CAT2 marks to determine the highest score.
     - Output the student’s ID, name, and highest mark for each student.
    
## Process of Execution

1. **Creating Directory in HDFS:**
   - Create a directory named `da1` in HDFS.

2. **Copying Input Files:**
   - Copy the input files from the local directory to HDFS.

3. **Running the Command:**
   - Execute the MapReduce job command to process the data.

### OUTPUT



![output1](https://github.com/user-attachments/assets/7570fe50-db11-45a0-88d8-bae5c7a78e1a)



![output2](https://github.com/user-attachments/assets/62e9dbdd-b749-4084-a176-1be19ad14686)



# **Shagbot User Guide 🚀**
Hi there! *Shagbot* is your personal task manager, making it effortless to add, track, and manage tasks! This project is created as part of the **NUS CS2103T Software Engineering Module.**

---

## **1️⃣ Goal**

Shagbot helps users efficiently manage and organize their daily tasks, ensuring they never miss deadlines or forget important events.
---
![My showcase](https://github.com/iamanoob44/ip/raw/master/docs/Ui.png)
---

## **2️⃣ Get Started**

### **To get started with shagbot,**
1. **Go to v0.3, click [here](https://github.com/iamanoob44/ip/releases "here")**
2. **Download the files and navigate** to the directory which contains `shagbot.jar`.
3. **Run** `java -jar shagbot.jar`
4. **Enjoy** the various features!

---

## **3️⃣ Details**

### **⚡ Basic Commands**

| **Command** | **Purpose** | **Example** |
|------------|------------|------------|
| `list` | Displays all tasks. | `list` |
| `bye` | Exits the application. | `bye` |

### **📝 Task Management**

📌 **All date-time inputs must follow this format:** `DD/M/YYYY HHHH` *(Example: 17/2/2025 1530)*

#### **✅ Adding your Tasks**

| **Command** | **Purpose** | **Example** | **Output** |
|------------|------------|------------|------------|
| `todo <task_name>` | Adds a new To-Do task. | `todo Buy groceries` | ```Got it. I've added this task: [T][ ] Buy groceries```
| `deadline <task_name> /by <due_date-time>` | Adds a new task with deadline. | `deadline CS2103T Assignment /by 20/2/2025 2359` | ``` Got it. I've added this task: [D][ ] CS2103T Assignment (by: Feb 20 2025, 11:59pm)```
| `event <task_name> /from <start_date-time> /to <end_date-time>` | Adds a new Event. | `event Project meeting /from 20/3/2025 1400 /to 20/3/2025 1600` | ```Got it. I've added this task: [E][ ] Project meeting (from: Mar 20 2025, 2:00pm to: Mar 20 2025, 4:00pm)```

#### **🔧 Managing your Tasks**

| **Command** | **Purpose** | **Example** |
|------------|------------|------------|
| `mark <task_number>` | Marks a task as completed. | `mark 2` |
| `unmark <task_number>` | Marks a task as incomplete. | `unmark 2` |
| `delete <task_number>` | Removes a task. | `delete 3` |
| `snooze <task_number> /by <new_due_date-time>` | Postpones the deadline of the task. | `snooze 2 /by 28/2/2025 1800` |
| `snooze <task_number> /from <new_start_date-time> /to <new_end_date-time>` | Reschedules an event. | `snooze 4 /from 25/3/2025 1000 /to 25/3/2025 1200` |
| `task on <DD/M/YYYY>` | Searches for tasks on a specific date. | `task on 20/3/2025` |

#### **🔍 Search & Reminders**

| **Command** | **Purpose** | **Example** |
|------------|------------|------------|
| `find <keyword>` | Searches for tasks given a keyword. | `find meeting` |
| `reminder` | Shows tasks due within the next 48 hours. | `reminder` |

---

Shagbot keeps you **organized and on track** so nothing slips through the cracks! 💡

---
Credits/Attribution:

Photo by <a href="https://unsplash.com/@ninjason?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Jason Leung</a> on <a href="https://unsplash.com/photos/yellow-and-gray-robot-toy-1DjbGRDh7-E?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Unsplash</a>

Photo by <a href="https://unsplash.com/@christnerfurt?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Christian Erfurt</a> on <a href="https://unsplash.com/photos/man-covering-face-with-both-hands-while-sitting-on-bench-sxQz2VfoFBE?utm_content=creditCopyText&utm_medium=referral&utm_source=unsplash">Unsplash</a>
      

      

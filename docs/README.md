# **Shagbot User Guide 🚀**
*Shagbot* is your personal task manager, making it effortless to add, track, and manage tasks! This project is created as part of the **NUS CS2103T Software Engineering Module.**

---

## **1️⃣ Goal**
Shagbot helps users efficiently manage and organize their daily tasks, ensuring they never miss deadlines or forget important events.

---

## **2️⃣ Get Started**
### **To get started with shagbot,**
1. **Go [here](https://github.com/iamanoob44/ip/releases "here")**
2. **Navigate** to the directory which contains `shagbot.jar`.
3. **Run `java -jar shagbot.jar` **
4. **Enjoy** the various features!

---

## **3️⃣ Details**
### **⚡ Basic Commands**
| **Command** | **Purpose** | **Example** |
|------------|------------|------------|
| `list` | Display all tasks. | `list` |
| `bye` | Exit the application. | `bye` |

### **📝 Task Management**
📌 **All date-time inputs must follow this format:** `DD/M/YYYY HHHH` *(Example: 17/2/2025 1530)*

#### **✅ Adding your Tasks**
| **Command** | **Purpose** | **Example** |
|------------|------------|------------|
| `todo <task_name>` | Adds a new To-Do task. | `todo Buy groceries` |
| `deadline <task_name> /by <due_date-time>` | Adds a new Deadline task. | `deadline CS2103T Assignment /by 20/2/2025 2359` |
| `event <task_name> /from <start_date-time> /to <end_date-time>` | Add an Event. | `event Project meeting /from 20/3/2025 1400 /to 20/3/2025 1600` |

#### **🔧 Managing your Tasks**
| **Command** | **Purpose** | **Example** |
|------------|------------|------------|
| `mark <task_number>` | Marks a task as completed. | `mark 2` |
| `unmark <task_number>` | Marks a task as incomplete. | `unmark 2` |
| `delete <task_number>` | Removes a task. | `delete 3` |
| `snooze <task_number> /by <new_due_date-time>` | Postpones the deadline of the task. | `snooze 2 /by 28/2/2025 1800` |
| `snooze <task_number> /from <new_start_date-time> /to <new_end_date-time>` | Reschedules an event. | `snooze 4 /from 25/3/2025 1000 /to 25/3/2025 1200` |
| `task on <DD/M/YYYY>` | Search for tasks on a specific date. | `task on 20/3/2025` |

#### **🔍 Search & Reminders**
| **Command** | **Purpose** | **Example** |
|------------|------------|------------|
| `find <keyword>` | Search for tasks with a keyword. | `find meeting` |
| `reminder` | Show tasks due in the next 48 hours. | `reminder` |

---

Shagbot keeps you **organized and on track** so nothing slips through the cracks! 💡
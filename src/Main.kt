import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.system.exitProcess

data class Book(
    val id: String,
    var title: String,
    var author: String,
    var totalCopies: Int,
    var availableCopies: Int
)

data class Member(
    val id: String,
    var name: String
)

data class Loan(
    val memberId: String,
    val bookId: String,
    val time: LocalDateTime
)

private val DB_FILE = File("library.db")
private val dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

// In-memory storage
private val books = mutableMapOf<String, Book>()
private val members = mutableMapOf<String, Member>()
private val loans = mutableListOf<Loan>()

fun main() {
    loadLibrary() // load saved data if any
    println("=== Simple Kotlin Library Management ===")

    while (true) {
        println(
            """
            |
            |Main Menu:
            | 1) Add Book
            | 2) Remove Book
            | 3) Register Member
            | 4) Lend Book
            | 5) Return Book
            | 6) Search Books
            | 7) List Books
            | 8) List Members
            | 9) Save Library
            | 0) Exit
            |Choose an option:
            """.trimMargin()
        )
        when (readLine()?.trim()) {
            "1" -> addBook()
            "2" -> removeBook()
            "3" -> registerMember()
            "4" -> lendBook()
            "5" -> returnBook()
            "6" -> searchBooks()
            "7" -> listBooks()
            "8" -> listMembers()
            "9" -> {
                saveLibrary()
                println("Saved.")
            }
            "0" -> {
                saveLibrary()
                println("Exiting. Data saved to ${DB_FILE.name}. Bye!")
                exitProcess(0)
            }
            else -> println("Invalid option.")
        }
    }
}

fun addBook() {
    print("Enter book id (unique): ")
    val id = readLine()?.trim().orEmpty()
    if (id.isEmpty()) {
        println("ID cannot be empty.")
        return
    }
    if (books.containsKey(id)) {
        println("A book with that ID already exists.")
        return
    }
    print("Enter title: ")
    val title = readLine()?.trim().orEmpty()
    print("Enter author: ")
    val author = readLine()?.trim().orEmpty()
    print("Enter number of copies: ")
    val copies = readLine()?.toIntOrNull() ?: run {
        println("Invalid number.")
        return
    }
    val book = Book(id, title, author, copies, copies)
    books[id] = book
    println("Book added: $title by $author (copies: $copies)")
}

fun removeBook() {
    print("Enter book id to remove: ")
    val id = readLine()?.trim().orEmpty()
    val b = books[id]
    if (b == null) {
        println("Book not found.")
        return
    }
    // Prevent removing if copies are currently lent out
    val lentCount = loans.count { it.bookId == id }
    if (lentCount > 0) {
        println("Cannot remove book — $lentCount copy(ies) are currently lent out.")
        return
    }
    books.remove(id)
    println("Book removed: ${b.title}")
}

fun registerMember() {
    print("Enter member id (unique): ")
    val id = readLine()?.trim().orEmpty()
    if (id.isEmpty()) {
        println("ID cannot be empty.")
        return
    }
    if (members.containsKey(id)) {
        println("Member with this ID already exists.")
        return
    }
    print("Enter member name: ")
    val name = readLine()?.trim().orEmpty()
    members[id] = Member(id, name)
    println("Member registered: $name (id: $id)")
}

fun lendBook() {
    print("Enter member id: ")
    val memberId = readLine()?.trim().orEmpty()
    val member = members[memberId]
    if (member == null) {
        println("Member not found. Register first.")
        return
    }
    print("Enter book id to lend: ")
    val bookId = readLine()?.trim().orEmpty()
    val book = books[bookId]
    if (book == null) {
        println("Book not found.")
        return
    }
    if (book.availableCopies <= 0) {
        println("No available copies to lend.")
        return
    }
    // Check if member already has this book (simple policy)
    val already = loans.any { it.memberId == memberId && it.bookId == bookId }
    if (already) {
        println("This member already borrowed this book.")
        return
    }
    book.availableCopies -= 1
    val loan = Loan(memberId, bookId, LocalDateTime.now())
    loans.add(loan)
    println("Lent '${book.title}' to ${member.name} at ${loan.time.format(dateFmt)}")
}

fun returnBook() {
    print("Enter member id: ")
    val memberId = readLine()?.trim().orEmpty()
    val member = members[memberId]
    if (member == null) {
        println("Member not found.")
        return
    }
    print("Enter book id to return: ")
    val bookId = readLine()?.trim().orEmpty()
    val book = books[bookId]
    if (book == null) {
        println("Book not found in library records.")
        return
    }
    val loanIndex = loans.indexOfFirst { it.memberId == memberId && it.bookId == bookId }
    if (loanIndex == -1) {
        println("No record of this book being borrowed by this member.")
        return
    }
    loans.removeAt(loanIndex)
    book.availableCopies += 1
    println("Book returned: '${book.title}'. Thank you, ${member.name}.")
}

fun searchBooks() {
    println("Search by: 1) ID  2) Title  3) Author")
    when (readLine()?.trim()) {
        "1" -> {
            print("Enter ID: ")
            val id = readLine()?.trim().orEmpty()
            val b = books[id]
            if (b == null) println("Not found.") else printBook(b)
        }
        "2" -> {
            print("Enter title keyword: ")
            val kw = readLine()?.trim().orEmpty().lowercase()
            val found = books.values.filter { it.title.lowercase().contains(kw) }
            if (found.isEmpty()) println("No matches.") else found.forEach { printBook(it) }
        }
        "3" -> {
            print("Enter author keyword: ")
            val kw = readLine()?.trim().orEmpty().lowercase()
            val found = books.values.filter { it.author.lowercase().contains(kw) }
            if (found.isEmpty()) println("No matches.") else found.forEach { printBook(it) }
        }
        else -> println("Invalid option.")
    }
}

fun listBooks() {
    if (books.isEmpty()) {
        println("No books in library.")
        return
    }
    println("---- Books ----")
    books.values.forEach { printBook(it) }
}

fun listMembers() {
    if (members.isEmpty()) {
        println("No members registered.")
        return
    }
    println("---- Members ----")
    members.values.forEach { println("- ${it.id}: ${it.name}") }
}

fun printBook(b: Book) {
    println("${b.id} | ${b.title} | ${b.author} | total: ${b.totalCopies} | avail: ${b.availableCopies}")
}

// Persistence format (simple):
// Each line: BOOK|id|title|author|total|available
// or MEMBER|id|name
fun saveLibrary() {
    val sb = StringBuilder()
    books.values.forEach { b ->
        val safeTitle = b.title.replace("|", " ")
        val safeAuthor = b.author.replace("|", " ")
        sb.append("BOOK|${b.id}|$safeTitle|$safeAuthor|${b.totalCopies}|${b.availableCopies}\n")
    }
    members.values.forEach { m ->
        val safeName = m.name.replace("|", " ")
        sb.append("MEMBER|${m.id}|$safeName\n")
    }
    // We don't persist loans in this simple version.
    DB_FILE.writeText(sb.toString())
}

fun loadLibrary() {
    if (!DB_FILE.exists()) return
    DB_FILE.readLines().forEach { line ->
        val parts = line.split('|')
        if (parts.isEmpty()) return@forEach
        when (parts[0]) {
            "BOOK" -> {
                if (parts.size >= 6) {
                    val id = parts[1]
                    val title = parts[2]
                    val author = parts[3]
                    val total = parts[4].toIntOrNull() ?: 0
                    val avail = parts[5].toIntOrNull() ?: total
                    books[id] = Book(id, title, author, total, avail)
                }
            }
            "MEMBER" -> {
                if (parts.size >= 3) {
                    val id = parts[1]
                    val name = parts[2]
                    members[id] = Member(id, name)
                }
            }
        }
    }
}
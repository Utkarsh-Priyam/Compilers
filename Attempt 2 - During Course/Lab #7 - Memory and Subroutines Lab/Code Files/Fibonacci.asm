# This is my solution to the fourth exercise to write a procedure that finds the nth Fibonacci number
# @author Utkarsh Priyam
# @version 5/15/20

	.text 0x00400000
	.globl main

# The main procedure
main:
	# Get the index (n) into $a0
	li $v0 5
	syscall
	move $a0 $v0
	# Call fib
	subu $sp, $sp, 4
	sw $ra, ($sp)
	jal fib
	lw $ra, ($sp)
	addu $sp, $sp, 4
	# Print the result
	move $a0 $v0
	li $v0 1
	syscall
	# Terminate
	li $v0 10
	syscall

# Finds the nth Fibonacci number (n is in $a0) and returns value in $v0
fib:
	# Base Case: F1 = 1
	ble $a0 1 base
	# Recursively call fib(n-1)
	subu $a0 $a0 1
	subu $sp, $sp, 4
	sw $ra, ($sp)
	jal fib
	lw $ra, ($sp)
	addu $sp, $sp, 4
	# Store result value into stack (b/c fib is recursive, so any normal register would be overriden)
	subu $sp, $sp, 4
	sw $v0, ($sp)
	# Recursively call fib(n-2)
	subu $a0 $a0 1
	subu $sp, $sp, 4
	sw $ra, ($sp)
	jal fib
	lw $ra, ($sp)
	addu $sp, $sp, 4
	# Add 2 recursive results together, leave sum in $v0
	addu $a0 $a0 2
	lw $t0, ($sp)
	addu $sp, $sp, 4
	addu $v0 $v0 $t0
	j end
base:
	li $v0 1
end:
	jr $ra

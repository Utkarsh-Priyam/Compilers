# This is my solution to the third exercise to write a procedure that finds the factorial of a given number
# @author Utkarsh Priyam
# @version 5/15/20

	.text 0x00400000
	.globl main

# The main procedure
main:
	# Get number (n) to get factorial of into $a0
	li $v0 5
	syscall
	move $a0 $v0
	# Call fact
	subu $sp, $sp, 4
	sw $ra, ($sp)
	jal fact
	lw $ra, ($sp)
	addu $sp, $sp, 4
	# Print n! (from $v0)
	move $a0 $v0
	li $v0 1
	syscall
	# Terminate
	li $v0 10
	syscall

# Finds the factorial of the number stored in $a0, and returns value to $v0
# Beware 32bit overflow
fact:
	# Base Case: 0! = 1
	ble $a0 0 base
	# Recursively call fact
	subu $a0 $a0 1
	subu $sp, $sp, 4
	sw $ra, ($sp)
	jal fact
	lw $ra, ($sp)
	addu $sp, $sp, 4
	# Multiply n to (n-1)!
	addu $a0 $a0 1
	mult $a0 $v0
	# Store n! into $v0
	mflo $v0
	j end
base:
	li $v0 1
end:
	jr $ra

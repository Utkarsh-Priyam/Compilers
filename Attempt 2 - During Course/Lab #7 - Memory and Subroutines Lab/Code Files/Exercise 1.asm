# This is my solution to the first exercise to write a procedure that finds the maximum of 2 numbers
# @author Utkarsh Priyam
# @version 5/15/20

	.text 0x00400000
	.globl main

# The main procedure
main:
	# get num1 into $a0
	li $v0 5
	syscall
	move $a0 $v0
	# get num2 into $a1
	li $v0 5
	syscall
	move $a1 $v0
	# call max2
	subu $sp, $sp, 4
	sw $ra, ($sp)
	jal max2
	lw $ra, ($sp)
	addu $sp, $sp, 4
	# print max from $v0
	move $a0 $v0
	li $v0 1
	syscall
	# terminate
	li $v0 10
	syscall

# Procedure takes 2 numbers (in $a0 and $a1) and returns the larger number (in $v0)
max2:
	blt $a0 $a1 second
	move $v0 $a0
	j end
second:
	move $v0 $a1
end:
	jr $ra

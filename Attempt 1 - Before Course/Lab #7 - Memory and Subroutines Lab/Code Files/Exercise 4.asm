.text 0x00400000
.globl main
main:
li $v0 5
syscall
move $a0 $v0
subu $sp, $sp, 4
sw $ra, ($sp)
jal fib
lw $ra, ($sp)
addu $sp, $sp, 4
move $a0 $v0
li $v0 1
syscall
li $v0 10
syscall

fib:
ble $a0 1 base
subu $a0 $a0 1
subu $sp, $sp, 4
sw $ra, ($sp)
jal fib
lw $ra, ($sp)
addu $sp, $sp, 4
subu $sp, $sp, 4
sw $v0, ($sp)
subu $a0 $a0 1
subu $sp, $sp, 4
sw $ra, ($sp)
jal fib
lw $ra, ($sp)
addu $sp, $sp, 4
addu $a0 $a0 2
lw $t0, ($sp)
addu $sp, $sp, 4
addu $v0 $v0 $t0
j end
base:
move $v0 $a0
end:
jr $ra